package com.architectcoders.arquitectomarvel.ui.main

import android.view.View
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.database.character.dbItemComics
import com.architectcoders.arquitectomarvel.model.database.character.dbObject
import com.architectcoders.arquitectomarvel.model.database.character.relations.ResultWithItemsComics
import com.architectcoders.arquitectomarvel.model.database.character.relations.toListResult
import com.architectcoders.arquitectomarvel.ui.common.Scope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

class MainPresenter(private val repository: Repository) : Scope by Scope.Impl() {

    var view: View? = null
    private val dao by lazy { repository.dao }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun updateData(list: List<Result>)
        fun initViews()
        fun showToast(msgResource: Int)
        fun navigateTo(result: Result, view: android.view.View)
    }

    fun onCreate(view: View) {
        this.view = view
        initScope()
        view.initViews()

        launch {
            view.showProgress()
            try {
                val characters = repository.getCharactersRemote()
                val resultsRemote = characters.characterData?.results ?: emptyList()

                resultsRemote.forEach { result ->
                    dao.insertResult(result.dbObject)
                    val collectionUri = result.comics.collectionURI
                    result.comics.items.forEach { item ->
                        dao.insertComics(item.dbItemComics(collectionUri))
                    }
                }
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                view.showToast(R.string.no_internet)
            }

            val localList = mutableListOf<ResultWithItemsComics>()
            val tmpResult = dao.getResults()
            tmpResult.forEach {
                val itemForLocalList = dao.getResultWithItemsComics(it.comicsCollectionURI)
                localList.addAll(itemForLocalList)
            }
            view.updateData(localList.toListResult)
            view.hideProgress()
        }
    }

    fun onResultClick(result: Result, view: android.view.View) {
        this.view?.navigateTo(result, view)
    }

    fun onDestroy() {
        cancelScope()
        view = null
    }
}

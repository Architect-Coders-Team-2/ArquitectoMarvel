package com.architectcoders.arquitectomarvel.ui.main

import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.database.dbItemComics
import com.architectcoders.arquitectomarvel.model.database.dbObject
import com.architectcoders.arquitectomarvel.model.database.relations.ResultWithItemsComics
import com.architectcoders.arquitectomarvel.model.database.relations.toListResult
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
        fun showToast(msg: String)
        fun navigateTo(result: Result)
    }

    fun onCreate(view: View) {
        this.view = view
        initScope()
        view.initViews()

        launch {
            view.showProgress()
            try {
                val characters = repository.getCharactersRemote()
                val resultsRemote = characters.data?.results ?: emptyList()

                resultsRemote.forEach { result ->
                    dao.insertResult(result.dbObject)
                    val colectionUri = result.comics.collectionURI
                    result.comics.items.forEach { item ->
                        dao.insertComics(item.dbItemComics(colectionUri))
                    }
                }
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                view.showToast("Sin conexión (DB data)")
            }

            val listaLocal = mutableListOf<ResultWithItemsComics>()
            val tmpResult = dao.getResults()
            tmpResult.forEach {
                val itemForListaLocal = dao.getResultWithItemsComics(it.comicsCollectionURI)
                listaLocal.addAll(itemForListaLocal)
            }
            view.updateData(listaLocal.toListResult)
            view.hideProgress()
        }
    }

    fun onResultClick(result: Result) {
        view?.navigateTo(result)
    }

    fun onDestroy() {
        cancelScope()
        view = null
    }

}
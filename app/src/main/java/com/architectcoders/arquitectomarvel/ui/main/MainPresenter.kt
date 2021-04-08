package com.architectcoders.arquitectomarvel.ui.main

import com.architectcoders.arquitectomarvel.R
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
                view.updateData(resultsRemote)
                view.hideProgress()
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                view.showToast(R.string.no_internet)
            }
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

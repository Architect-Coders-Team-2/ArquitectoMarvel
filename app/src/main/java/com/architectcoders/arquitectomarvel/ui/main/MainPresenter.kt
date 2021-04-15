package com.architectcoders.arquitectomarvel.ui.main

import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.characters.Result
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
        fun showToastResource(msgResource: Int)
        fun showToastString(msgString: String)
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
                view.showToastResource(R.string.no_internet)
                view.hideProgress()
            } catch (e: Exception) {
                view.showToastString(e.message?: e.toString())
                view.hideProgress()
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

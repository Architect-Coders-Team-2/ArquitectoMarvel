package com.architectcoders.arquitectomarvel.ui.detail

import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.comics.Result
import com.architectcoders.arquitectomarvel.ui.common.Scope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

class HeroDetailPresenter(val repository: Repository) : Scope by Scope.Impl() {

    interface View {
        fun setHeroDetails()
        fun showProgress()
        fun hideProgress()
        fun showToast(msgResource: Int)
        fun updateComics(comicList: List<Result>)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        this.view = view
        initScope()
        view.setHeroDetails()
    }

    fun onDestroy() {
        cancelScope()
        view = null
    }

    fun onFabClick() {
        // todo
    }

    fun onHeroShown(heroId: Int) {
        launch {
            try {
                view?.showProgress()
                val comic = repository.getComicsFromCharacterRemote(heroId)
                val comicList = comic?.comicData?.results ?: emptyList()
                view?.updateComics(comicList)
                view?.hideProgress()
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                view?.showToast(R.string.no_internet)
            }
        }
    }
}

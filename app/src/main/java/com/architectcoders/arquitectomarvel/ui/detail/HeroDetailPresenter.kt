package com.architectcoders.arquitectomarvel.ui.detail

import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.comics.Result
import com.architectcoders.arquitectomarvel.model.database.DetailedComicEntity
import com.architectcoders.arquitectomarvel.ui.common.Scope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

class HeroDetailPresenter(private val repository: Repository) : Scope by Scope.Impl() {

    interface View {
        fun initComicListAdapter()
        fun setHeroDetails()
        fun updateFAB(isCharacterFavorite: Boolean)
        fun showProgress()
        fun hideProgress()
        fun showToast(msgResource: Int)
        fun updateComics(comicList: List<Result>)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        this.view = view
        initScope()
        view.initComicListAdapter()
        view.setHeroDetails()
    }

    /**
     * Checks if the current character is favorite and get the character's comics
     */
    fun onHeroShown(heroId: Int) {
        launch {
            try {
                view?.showProgress()
                val isCharacterFavorite = repository.isCharacterFavorite(heroId)
                view?.updateFAB(isCharacterFavorite)
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

    fun onFabClick(
        selectedHero: com.architectcoders.arquitectomarvel.model.characters.Result,
        comicList: MutableList<DetailedComicEntity>,
        isCharacterFavorite: Boolean
    ) {
        launch {
            if (isCharacterFavorite) {
                repository.insertFavoriteCharacter(selectedHero)
                comicList.forEach {
                    repository.insertFavoriteComic(it)
                }
            } else {
                repository.deleteFavoriteCharacters(selectedHero)
                comicList.forEach {
                    repository.deleteFavoriteDetailedComic(it)
                }
            }
        }
    }

    fun onDestroy() {
        cancelScope()
        view = null
    }
}

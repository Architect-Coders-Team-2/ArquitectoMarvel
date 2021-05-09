package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.*
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.local.entities.DetailedComicEntity
import com.architectcoders.arquitectomarvel.data.local.entities.fromDetailedComicEntityToDetailedComic
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.toCharacterResultDomain
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.Result
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.fromListResult
import com.architectcoders.module.usescases.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Result as CharacterResult

class HeroDetailViewModel(
    private val useCaseGetComicsRemote: UseCaseGetComicsRemote,
    private val useCaseInsertFavoriteCharacter: UseCaseInsertFavoriteCharacter,
    private val useCaseInsertFavoriteComic: UseCaseInsertFavoriteComic,
    private val useCaseIsCharacterFavorite: UseCaseIsCharacterFavorite,
    private val useCaseDeleteFavoriteCharacter: UseCaseDeleteFavoriteCharacter,
    private val useCaseDeleteFavoriteDetailComic: UseCaseDeleteFavoriteDetailComic
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class SetHeroDetails(val onHeroShown: (Int) -> Unit) : UiModel()
        class UpdateFAB(
            val isCharacterFavorite: Boolean,
            val listener: (
                selectedHero: CharacterResult,
                comicList: MutableList<DetailedComicEntity>,
                isCharacterFavorite: Boolean,
            ) -> Unit,
        ) : UiModel()

        class ShowToast(val msgResource: Int) : UiModel()
        class UpdateComics(val comicList: List<Result>) : UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.SetHeroDetails(::onHeroShown)
    }

    /**
     * Checks if the current character is favorite and get the character's comics
     */
    private fun onHeroShown(heroId: Int) {
        viewModelScope.launch {
            try {
                _model.value = UiModel.Loading
                val isCharacterFavorite = useCaseIsCharacterFavorite.invoke(heroId)
                _model.value = UiModel.UpdateFAB(isCharacterFavorite, ::onFabClick)
                val comic = useCaseGetComicsRemote.invoke(heroId)
                val comicList = comic.data?.results ?: emptyList()
                _model.value = UiModel.UpdateComics(comicList.fromListResult())
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                _model.value = UiModel.ShowToast(R.string.no_internet)
            }
        }
    }

    private fun onFabClick(
        selectedHero: CharacterResult,
        comicList: MutableList<DetailedComicEntity>,
        isCharacterFavorite: Boolean,
    ) {
        viewModelScope.launch {
            if (isCharacterFavorite) {
                useCaseInsertFavoriteCharacter.invoke(selectedHero.toCharacterResultDomain())
                comicList.forEach {
                    useCaseInsertFavoriteComic.invoke(it.fromDetailedComicEntityToDetailedComic())
                }
            } else {
                useCaseDeleteFavoriteCharacter.invoke(selectedHero.toCharacterResultDomain())
                comicList.forEach {
                    useCaseDeleteFavoriteDetailComic.invoke(it.fromDetailedComicEntityToDetailedComic())
                }
            }
        }
    }
}

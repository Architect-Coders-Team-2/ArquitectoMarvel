package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.*
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.comics.Result
import com.architectcoders.arquitectomarvel.model.database.DetailedComicEntity
import com.architectcoders.module.usescases.UseCaseGetCharactersRemote
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import com.architectcoders.arquitectomarvel.model.characters.Result as CharacterResult

class HeroDetailViewModel(private val repository: Repository) : ViewModel() {

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
                val isCharacterFavorite = repository.isCharacterFavorite(heroId)
                _model.value = UiModel.UpdateFAB(isCharacterFavorite, ::onFabClick)
                val comic = repository.getComicsFromCharacterRemote(heroId)
                val comicList = comic?.comicData?.results ?: emptyList()
                _model.value = UiModel.UpdateComics(comicList)
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
}



class VMFHero (val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return   modelClass.getConstructor(UseCaseGetCharactersRemote::class.java).newInstance(repository)
    }
}

package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.database.models.ComicEntity
import com.architectcoders.arquitectomarvel.data.database.models.toComicResult
import com.architectcoders.usecases.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class CharacterDetailViewModel(
    private val getCharacterById: GetCharacterById,
    private val isCharacterFavorite: IsCharacterFavorite,
    private val getComicsFromCharacterId: GetComicsFromCharacterId,
    private val insertFavoriteCharacter: InsertFavoriteCharacter,
    private val insertFavoriteComic: InsertFavoriteComic,
    private val deleteFavoriteCharacter: DeleteFavoriteCharacter,
    private val deleteFavoriteComic: DeleteFavoriteComic
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class RequestCharacterById(val listener: (Int) -> Unit) : UiModel()
        class SetCharacterDetails(val character: CharacterResult?) : UiModel()
        class UpdateFAB(
            val isCharacterFavorite: Boolean,
            val listener: (
                selectedHero: CharacterResult,
                comicList: MutableList<ComicEntity>,
                isCharacterFavorite: Boolean,
            ) -> Unit,
        ) : UiModel()

        class ShowToast(val msgResource: Int) : UiModel()
        class UpdateComics(val comicList: List<ComicResult>) : UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.RequestCharacterById(::onIdCollected)
    }

    private fun onIdCollected(characterId: Int) {
        viewModelScope.launch {
            try {
                _model.value = UiModel.Loading
                getCharacterId(characterId)
                isCharacterFavorite(characterId)
                _model.value = UiModel.Loading
                getComicsFromCharacterId(characterId)
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
                _model.value = UiModel.ShowToast(R.string.no_internet)
            }
        }
    }

    private suspend fun getCharacterId(characterId: Int) {
        val characters = getCharacterById.invoke(characterId)
        _model.value = UiModel.SetCharacterDetails(characters.characterData?.results?.first())
    }

    private suspend fun isCharacterFavorite(characterId: Int) {
        val isCharacterFavorite = isCharacterFavorite.invoke(characterId)
        _model.value = UiModel.UpdateFAB(isCharacterFavorite, ::onFabClick)
    }

    private suspend fun getComicsFromCharacterId(characterId: Int) {
        val comic = getComicsFromCharacterId.invoke(characterId)
        val comicList = comic?.comicData?.results ?: emptyList()
        _model.value = UiModel.UpdateComics(comicList)
    }

    private fun onFabClick(
        selectedHero: CharacterResult,
        comicList: MutableList<ComicEntity>,
        isCharacterFavorite: Boolean,
    ) {
        viewModelScope.launch {
            if (isCharacterFavorite) {
                insertFavoriteCharacter.invoke(selectedHero)
                comicList.forEach {
                    insertFavoriteComic.invoke(it.toComicResult)
                }
            } else {
                deleteFavoriteCharacter.invoke(selectedHero)
                comicList.forEach {
                    deleteFavoriteComic.invoke(it.toComicResult)
                }
            }
        }
    }
}

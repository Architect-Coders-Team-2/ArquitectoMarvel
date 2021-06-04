package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.usecases.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import com.architectcoders.domain.comics.Comic as ComicResult
import com.architectcoders.domain.heros.Hero as CharacterResult

class CharacterDetailViewModel(
    private val characterId: Int,
    private val getLocalCharacterById: GetLocalCharacterById,
    private val isLocalCharacterFavorite: IsLocalCharacterFavorite,
    private val getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
    private val insertLocalFavoriteCharacter: InsertLocalFavoriteCharacter,
    private val insertLocalFavoriteComic: InsertLocalFavoriteComic,
    private val deleteLocalFavoriteCharacter: DeleteLocalFavoriteCharacter,
    private val deleteLocalFavoriteComic: DeleteLocalFavoriteComic
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class SetCharacterDetails(val character: CharacterResult) : UiModel()
        class UpdateFAB(
            val isCharacterFavorite: Boolean,
            val listener: (
                selectedHero: CharacterResult,
                comicList: MutableList<ComicResult>,
                isCharacterFavorite: Boolean,
            ) -> Unit,
        ) : UiModel()

        class ShowToast(val msgResource: Int) : UiModel()
        class UpdateComics(val comicList: List<ComicResult>) : UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.Loading
        loadCharacterId(characterId)
    }

    private fun loadCharacterId(characterId: Int) {
        viewModelScope.launch {
            try {
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
        val character = getLocalCharacterById.invoke(characterId)
        _model.value = UiModel.SetCharacterDetails(character)
    }

    private suspend fun isCharacterFavorite(characterId: Int) {
        val isCharacterFavorite = isLocalCharacterFavorite.invoke(characterId)
        _model.value = UiModel.UpdateFAB(isCharacterFavorite, ::onFabClick)
    }

    private suspend fun getComicsFromCharacterId(characterId: Int) {
        val comic = getRemoteComicsFromCharacterId.invoke(characterId)
        val comicList = comic?.comicDataComics?.comics ?: emptyList()
        _model.value = UiModel.UpdateComics(comicList)
    }

    private fun onFabClick(
        selectedHero: CharacterResult,
        comicList: MutableList<ComicResult>,
        isCharacterFavorite: Boolean,
    ) {
        viewModelScope.launch {
            if (isCharacterFavorite) {
                insertLocalFavoriteCharacter.invoke(selectedHero)
                comicList.forEach {
                    insertLocalFavoriteComic.invoke(it)
                }
            } else {
                deleteLocalFavoriteCharacter.invoke(selectedHero)
                comicList.forEach {
                    deleteLocalFavoriteComic.invoke(it)
                }
            }
        }
    }
}

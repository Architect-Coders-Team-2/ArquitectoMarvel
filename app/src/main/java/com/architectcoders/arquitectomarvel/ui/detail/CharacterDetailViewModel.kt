package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic
import com.architectcoders.usecases.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

class CharacterDetailViewModel(
    private val characterId: Int,
    private val getLocalCharacterById: GetLocalCharacterById,
    private val isLocalCharacterFavorite: IsLocalCharacterFavorite,
    private val getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
    private val insertLocalFavoriteCharacter: InsertLocalFavoriteCharacter,
    private val deleteLocalFavoriteCharacter: DeleteLocalFavoriteCharacter,
    private val getComicsInteractor: GetComicsInteractor
) : ViewModel() {

    val comics get() = getComicsInteractor.invoke(characterId)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class SetCharacterDetails(val character: Character) : UiModel()
        class UpdateFAB(
            val isCharacterFavorite: Boolean,
            val listener: (
                selectedHero: Character,
                comicList: MutableList<Comic>,
                isCharacterFavorite: Boolean,
            ) -> Unit,
        ) : UiModel()

        class UpdateComics(val comicList: List<Comic>) : UiModel()
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
        val comicList = comic?.comicData?.comics ?: emptyList()
        _model.value = UiModel.UpdateComics(comicList)
    }

    private fun onFabClick(
        selectedHero: Character,
        comicList: MutableList<Comic>,
        isCharacterFavorite: Boolean,
    ) {
        viewModelScope.launch {
            if (isCharacterFavorite) {
                insertLocalFavoriteCharacter.invoke(selectedHero)
            } else {
                deleteLocalFavoriteCharacter.invoke(selectedHero)
            }
        }
    }
}

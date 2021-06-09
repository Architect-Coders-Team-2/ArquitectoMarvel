package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic
import com.architectcoders.usecases.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

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

    private val _uiModelCharacter: MutableStateFlow<UiModelCharacter> =
        MutableStateFlow(UiModelCharacter.Refresh)
    val uiModelCharacter: StateFlow<UiModelCharacter>
        get() {
            if (_uiModelCharacter.value == UiModelCharacter.Refresh) refresh()
            return _uiModelCharacter
        }

    private val _uiModelComic: MutableStateFlow<UiModelComic> =
        MutableStateFlow(UiModelComic.Loading)
    val uiModelComic: StateFlow<UiModelComic> get() = _uiModelComic

    sealed class UiModelCharacter {
        object Refresh : UiModelCharacter()
        object Loading : UiModelCharacter()
        class SetUiDetailsCharacter(
            val character: Character,
            val isCharacterFavorite: Flow<Int>,
            val listener: (
                selectedCharacter: Character,
                comicList: MutableList<Comic>,
                isCharacterFavorite: Boolean,
            ) -> Unit
        ) : UiModelCharacter()
    }

    sealed class UiModelComic {
        object Loading : UiModelComic()
        class UpdateComics(val comicList: List<Comic>) : UiModelComic()
    }

    private fun refresh() {
        _uiModelCharacter.value = UiModelCharacter.Loading
        loadCharacterId(characterId)
    }

    private fun loadCharacterId(characterId: Int) {
        viewModelScope.launch {
            try {
                loadCharacterById(characterId)
                loadComicsFromCharacterId(characterId)
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
            }
        }
    }

    private suspend fun loadCharacterById(characterId: Int) {
        val character = getLocalCharacterById.invoke(characterId)
        val isCharacterFavorite = isLocalCharacterFavorite.invoke(characterId) as Flow<Int>
        _uiModelCharacter.value =
            UiModelCharacter.SetUiDetailsCharacter(character, isCharacterFavorite, ::onFabClick)
    }

    private suspend fun loadComicsFromCharacterId(characterId: Int) {
        _uiModelComic.value = UiModelComic.Loading
        val comic = getRemoteComicsFromCharacterId.invoke(characterId)
        val comicList = comic?.comicData?.comics ?: emptyList()
        _uiModelComic.value = UiModelComic.UpdateComics(comicList)
    }

    private fun onFabClick(
        selectedCharacter: Character,
        comicList: MutableList<Comic>,
        isCharacterFavorite: Boolean,
    ) {
        viewModelScope.launch {
            if (isCharacterFavorite) {
                insertLocalFavoriteCharacter.invoke(selectedCharacter)
                comicList.forEach {
                    insertLocalFavoriteComic.invoke(it)
                }
            } else {
                deleteLocalFavoriteCharacter.invoke(selectedCharacter)
                comicList.forEach {
                    deleteLocalFavoriteComic.invoke(it)
                }
            }
        }
    }
}

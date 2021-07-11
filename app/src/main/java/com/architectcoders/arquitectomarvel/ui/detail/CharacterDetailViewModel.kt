package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.domain.character.Character
import com.architectcoders.usecases.DeleteLocalFavoriteCharacter
import com.architectcoders.usecases.GetLocalCharacterById
import com.architectcoders.usecases.InsertLocalFavoriteCharacter
import com.architectcoders.usecases.IsLocalCharacterFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    @Named("characterId") private val characterId: Int,
    private val getLocalCharacterById: GetLocalCharacterById,
    private val isLocalCharacterFavorite: IsLocalCharacterFavorite,
    private val insertLocalFavoriteCharacter: InsertLocalFavoriteCharacter,
    private val deleteLocalFavoriteCharacter: DeleteLocalFavoriteCharacter,
    getComicsInteractor: GetComicsInteractor
) : ViewModel() {

    val comicResurce = getComicsInteractor.networkBoundResourceResult(characterId)
    private val _uiModel: MutableStateFlow<UiModel> =
        MutableStateFlow(UiModel.Refresh)
    val uiModel: StateFlow<UiModel>
        get() {
            if (_uiModel.value == UiModel.Refresh) refresh()
            return _uiModel
        }

    sealed class UiModel {
        object Refresh : UiModel()
        object Loading : UiModel()
        class SetUiDetails(
            val character: Character,
            val isCharacterFavorite: Flow<Int>,
            val listener: (
                selectedCharacter: Character,
                isCharacterFavorite: Boolean
            ) -> Unit
        ) : UiModel()
    }

    private fun refresh() {
        _uiModel.value = UiModel.Loading
        loadCharacterById(characterId)
    }

    private fun loadCharacterById(characterId: Int) {
        viewModelScope.launch {
            try {
                val character = getLocalCharacterById.invoke(characterId)
                val isCharacterFavorite = isLocalCharacterFavorite.invoke(characterId) as Flow<Int>
                _uiModel.value =
                    UiModel.SetUiDetails(character, isCharacterFavorite, ::onFabClick)
            } catch (e: UnknownHostException) {
                Timber.e("qq_MainPresenter.onCreate: $e")
            }
        }
    }

    private fun onFabClick(
        selectedCharacter: Character,
        isCharacterFavorite: Boolean,
    ) {
        viewModelScope.launch {
            if (isCharacterFavorite) {
                insertLocalFavoriteCharacter.invoke(selectedCharacter)
            } else {
                deleteLocalFavoriteCharacter.invoke(selectedCharacter)
            }
        }
    }
}

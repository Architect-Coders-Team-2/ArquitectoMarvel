package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.domain.character.Character
import com.architectcoders.usecases.*
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
    private val getComicsInteractor: GetComicsInteractor,
    private val handleNetworkManager: HandleNetworkManager
) : ViewModel() {

    val comicResurce = getComicsInteractor.networkBoundResourceResult(characterId)
    private val _uiNetworkModel: MutableStateFlow<UiNetworkModel> =
        MutableStateFlow(UiNetworkModel.Refresh)
    val uiNetworkModel: StateFlow<UiNetworkModel>
        get() {
            initNetworkManager()
            return _uiNetworkModel
        }

    private val _uiModel: MutableStateFlow<UiModel> =
        MutableStateFlow(UiModel.Refresh)
    val uiModel: StateFlow<UiModel>
        get() {
            if (_uiModel.value == UiModel.Refresh) {
                refresh()
            }
            return _uiModel
        }

    sealed class UiNetworkModel {
        object Refresh : UiNetworkModel()
        class InitNetworkManager(val listener: (Lifecycle) -> Unit) : UiNetworkModel()
        class SetNetworkAvailability(val isAvailable: Boolean) : UiNetworkModel()
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

    private fun initNetworkManager() {
        _uiNetworkModel.value = UiNetworkModel.InitNetworkManager(::manageNetworkManager)
    }

    private fun manageNetworkManager(lifecycle: Lifecycle) {
        viewModelScope.launch {
            handleNetworkManager.invoke(lifecycle, ::isNetworkAvailable)
        }
    }

    private fun isNetworkAvailable(isAvailable: Boolean) {
        _uiNetworkModel.value = UiNetworkModel.SetNetworkAvailability(isAvailable)
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

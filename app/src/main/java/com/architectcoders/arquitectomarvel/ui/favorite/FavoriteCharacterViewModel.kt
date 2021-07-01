package com.architectcoders.arquitectomarvel.ui.favorite

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.arquitectomarvel.data.database.FavoriteCharacterEntity
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import com.architectcoders.usecases.HandleNetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCharacterViewModel @Inject constructor(
    private val getLocalFavoriteCharacters: GetLocalFavoriteCharacters,
    private val handleNetworkManager: HandleNetworkManager
) : ViewModel() {

    private val _uiNetworkModel: MutableStateFlow<UiNetworkModel> =
        MutableStateFlow(UiNetworkModel.Refresh)
    val uiNetworkModel: StateFlow<UiNetworkModel>
        get() {
            initNetworkManager()
            return _uiNetworkModel
        }

    private val _uiModel: MutableStateFlow<UiModel> = MutableStateFlow(UiModel.Refresh)
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
        class RetrieveFavoriteCharacters(val favoriteCharacterList: Flow<List<FavoriteCharacterEntity>>) :
            UiModel()
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
        loadFavoriteCharacters()
    }

    private fun loadFavoriteCharacters() {
        viewModelScope.launch {
            val localFavoriteCharacters =
                getLocalFavoriteCharacters.invoke(Unit) as Flow<List<FavoriteCharacterEntity>>
            _uiModel.value = UiModel.RetrieveFavoriteCharacters(localFavoriteCharacters)
        }
    }
}

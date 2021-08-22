package com.architectcoders.arquitectomarvel.ui.favorite

import com.architectcoders.arquitectomarvel.data.database.FavoriteCharacterEntity
import com.architectcoders.arquitectomarvel.ui.common.ScopeViewModel
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCharacterViewModel @Inject constructor(
    coroutineDispatcher: CoroutineDispatcher,
    private val getLocalFavoriteCharacters: GetLocalFavoriteCharacters
) : ScopeViewModel(coroutineDispatcher) {

    private val _uiModel: MutableStateFlow<UiModel> = MutableStateFlow(UiModel.Refresh)
    val uiModel: StateFlow<UiModel>
        get() {
            if (_uiModel.value == UiModel.Refresh) {
                refresh()
            }
            return _uiModel
        }

    sealed class UiModel {
        object Refresh : UiModel()
        object Loading : UiModel()
        class RetrieveFavoriteCharacters(val favoriteCharacterList: Flow<List<FavoriteCharacterEntity>>) :
            UiModel()
    }

    private fun refresh() {
        _uiModel.value = UiModel.Loading
        loadFavoriteCharacters()
    }

    private fun loadFavoriteCharacters() {
        launch {
            val localFavoriteCharacters =
                getLocalFavoriteCharacters.invoke(Unit) as Flow<List<FavoriteCharacterEntity>>
            _uiModel.value = UiModel.RetrieveFavoriteCharacters(localFavoriteCharacters)
        }
    }
}

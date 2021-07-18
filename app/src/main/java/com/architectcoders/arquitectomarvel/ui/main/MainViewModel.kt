package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.architectcoders.arquitectomarvel.data.database.CharacterEntity
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@ExperimentalPagingApi
@Inject constructor(
    private val characterRemoteMediator: CharacterRemoteMediator,
    private val getPagingSourceFromCharacterEntity: GetPagingSourceFromCharacterEntity,
    private val handleNetworkManager: HandleNetworkManager
) : ViewModel() {
    @ExperimentalPagingApi
    val pager = Pager(
        config = PagingConfig(pageSize = REQUEST_LIMIT / 2),
        remoteMediator = characterRemoteMediator
    ) {
        getPagingSourceFromCharacterEntity.invoke() as PagingSource<Int, CharacterEntity>
    }.flow.cachedIn(viewModelScope)

    private val _uiModel: MutableStateFlow<UiModel> = MutableStateFlow(UiModel.Refresh)
    val uiModel: StateFlow<UiModel>
        get() {
            initNetworkManager()
            return _uiModel
        }

    sealed class UiModel {
        object Refresh : UiModel()
        class InitNetworkManager(val listener: (Lifecycle) -> Unit) : UiModel()
        class SetNetworkAvailability(val isAvailable: Boolean) : UiModel()
    }

    private fun initNetworkManager() {
        _uiModel.value = UiModel.InitNetworkManager(::manageNetworkManager)
    }

    private fun manageNetworkManager(lifecycle: Lifecycle) {
        viewModelScope.launch {
            handleNetworkManager.invoke(lifecycle, ::isNetworkAvailable)
        }
    }

    private fun isNetworkAvailable(isAvailable: Boolean) {
        _uiModel.value = UiModel.SetNetworkAvailability(isAvailable)
    }
}

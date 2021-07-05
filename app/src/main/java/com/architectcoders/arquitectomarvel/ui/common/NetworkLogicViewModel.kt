package com.architectcoders.arquitectomarvel.ui.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.usecases.HandleNetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkLogicViewModel @Inject constructor(
    private val handleNetworkManager: HandleNetworkManager
) : ViewModel() {

    private val _uiNetworkModel: MutableStateFlow<UiNetworkModel> =
        MutableStateFlow(UiNetworkModel.Refresh)
    val uiNetworkModel: StateFlow<UiNetworkModel>
        get() {
            initNetworkManager()
            return _uiNetworkModel
        }

    sealed class UiNetworkModel {
        object Refresh : UiNetworkModel()
        class InitNetworkManager(val listener: (Lifecycle) -> Unit) : UiNetworkModel()
        class SetNetworkAvailability(val isAvailable: Boolean) : UiNetworkModel()
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
}

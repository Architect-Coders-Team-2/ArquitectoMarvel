package com.architectcoders.arquitectomarvel.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architectcoders.usecases.HandleNetworkManager
import com.architectcoders.usecases.UnregisterNetworkCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkLogicViewModel @Inject constructor(
    private val handleNetworkManager: HandleNetworkManager,
    private val unregisterNetworkCallback: UnregisterNetworkCallback
) : ViewModel() {

    private val _uiNetworkModel: MutableStateFlow<UiNetworkModel> =
        MutableStateFlow(UiNetworkModel.Refresh)
    val uiNetworkModel: StateFlow<UiNetworkModel>
        get() {
            handleNetworkManager()
            return _uiNetworkModel
        }

    sealed class UiNetworkModel {
        object Refresh : UiNetworkModel()
        class SetNetworkAvailability(val isAvailable: Boolean) : UiNetworkModel()
    }

    private fun handleNetworkManager() {
        viewModelScope.launch {
            handleNetworkManager.invoke(::isNetworkAvailable)
        }
    }

    private fun isNetworkAvailable(isAvailable: Boolean) {
        _uiNetworkModel.value = UiNetworkModel.SetNetworkAvailability(isAvailable)
    }

    fun unregisterNetworkManager() {
        unregisterNetworkCallback.invoke()
    }
}

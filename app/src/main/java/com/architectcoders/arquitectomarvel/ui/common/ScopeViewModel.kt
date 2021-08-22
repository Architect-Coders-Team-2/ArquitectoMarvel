package com.architectcoders.arquitectomarvel.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

abstract class ScopeViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(),
    Scope by Scope.Impl(uiDispatcher) {

    init {
        initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}

package com.architectcoders.data.source

interface BiometricDataSource {
    fun setBiometricAuthentication(listener: () -> Unit)
    fun checkAuthenticationState()
}

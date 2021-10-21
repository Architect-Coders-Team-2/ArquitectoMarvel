package com.architectcoders.data.repository

import com.architectcoders.data.source.AuthenticationStateDataSource
import com.architectcoders.data.source.BiometricPromptDataSource
import javax.inject.Inject

class BiometricRepository @Inject constructor(
    private val authenticationStateDataSource: AuthenticationStateDataSource,
    private val biometricPromptDataSource: BiometricPromptDataSource
) {
    fun checkAuthenticationState(): Unit =
        authenticationStateDataSource.checkAuthenticationState()

    fun checkIfUserIsAlreadyAuthenticated(): Boolean =
        authenticationStateDataSource.checkIfUserIsAlreadyAuthenticated()

    fun saveAuthenticationState(isAuthenticated: Boolean): Unit =
        authenticationStateDataSource.saveAuthenticationState(isAuthenticated)

    fun canUserUseBiometricAuthentication(): Boolean =
        biometricPromptDataSource.canUserUseBiometricAuthentication()

    fun setBiometricAuthentication(onFail: () -> Unit, onSuccess: () -> Unit): Unit =
        biometricPromptDataSource.setBiometricAuthentication(onFail, onSuccess)
}

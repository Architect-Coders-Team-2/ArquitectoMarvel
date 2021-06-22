package com.architectcoders.data.repository

import com.architectcoders.data.source.BiometricDataSource

class BiometricRepository(private val biometricDataSource: BiometricDataSource) {

    fun setBiometricAuthentication(listener: () -> Unit): Unit =
        biometricDataSource.setBiometricAuthentication(listener)

    fun checkAuthenticationState() =
        biometricDataSource.checkAuthenticationState()
}

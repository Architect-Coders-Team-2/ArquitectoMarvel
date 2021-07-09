package com.architectcoders.data.repository

import com.architectcoders.data.source.BiometricDataSource

class BiometricRepository(private val biometricDataSource: BiometricDataSource) {

    fun setBiometricAuthentication(onFail: () -> Unit, onSuccess: () -> Unit): Unit =
        biometricDataSource.setBiometricAuthentication(onFail, onSuccess)
}

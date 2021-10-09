package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository

class SetBiometricAuthentication(private val biometricRepository: BiometricRepository) {
    fun invoke(onFail: () -> Unit, onSuccess: () -> Unit) =
        biometricRepository.setBiometricAuthentication(onFail, onSuccess)
}

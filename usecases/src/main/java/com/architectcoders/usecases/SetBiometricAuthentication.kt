package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository

class SetBiometricAuthentication(private val biometricRepository: BiometricRepository) {
    fun invoke(listener: () -> Unit) =
        biometricRepository.setBiometricAuthentication(listener)
}

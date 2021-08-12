package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository

class CanUserUseBiometricAuthentication(private val biometricRepository: BiometricRepository) {
    fun invoke(): Boolean = biometricRepository.canUserUseBiometricAuthentication()
}

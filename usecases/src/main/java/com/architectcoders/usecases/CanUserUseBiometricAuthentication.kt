package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository
import javax.inject.Inject

class CanUserUseBiometricAuthentication @Inject constructor(
    private val biometricRepository: BiometricRepository
) {
    fun invoke(): Boolean = biometricRepository.canUserUseBiometricAuthentication()
}

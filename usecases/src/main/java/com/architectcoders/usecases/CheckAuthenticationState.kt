package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository

class CheckAuthenticationState(private val biometricRepository: BiometricRepository) {
    fun invoke() =
        biometricRepository.checkAuthenticationState()
}

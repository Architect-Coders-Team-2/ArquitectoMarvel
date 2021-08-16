package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository

class SaveAuthenticationState(private val biometricRepository: BiometricRepository) {
    fun invoke(param: Boolean) =
        biometricRepository.saveAuthenticationState(param)
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository
import javax.inject.Inject

class CheckAuthenticationState @Inject constructor(
    private val biometricRepository: BiometricRepository
) {
    fun invoke() =
        biometricRepository.checkAuthenticationState()
}

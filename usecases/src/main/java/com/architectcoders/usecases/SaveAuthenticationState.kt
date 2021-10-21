package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository
import javax.inject.Inject

class SaveAuthenticationState @Inject constructor(
    private val biometricRepository: BiometricRepository
) {
    fun invoke(param: Boolean) =
        biometricRepository.saveAuthenticationState(param)
}

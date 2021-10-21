package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository
import javax.inject.Inject

class CheckIfUserIsAlreadyAuthenticated @Inject constructor(
    private val biometricRepository: BiometricRepository
) {
    fun invoke(): Boolean =
        biometricRepository.checkIfUserIsAlreadyAuthenticated()
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository

class CheckIfUserIsAlreadyAuthenticated(private val biometricRepository: BiometricRepository) {
    fun invoke(): Boolean =
        biometricRepository.checkIfUserIsAlreadyAuthenticated()
}

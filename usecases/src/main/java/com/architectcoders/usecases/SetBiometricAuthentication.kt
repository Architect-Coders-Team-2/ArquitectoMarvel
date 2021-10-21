package com.architectcoders.usecases

import com.architectcoders.data.repository.BiometricRepository
import javax.inject.Inject

class SetBiometricAuthentication @Inject constructor(
    private val biometricRepository: BiometricRepository
) {
    fun invoke(onFail: () -> Unit, onSuccess: () -> Unit) =
        biometricRepository.setBiometricAuthentication(onFail, onSuccess)
}

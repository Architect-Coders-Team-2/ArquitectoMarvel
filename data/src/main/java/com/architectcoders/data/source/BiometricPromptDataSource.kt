package com.architectcoders.data.source

interface BiometricPromptDataSource {
    fun canUserUseBiometricAuthentication(): Boolean
    fun setBiometricAuthentication(onFail: () -> Unit, onSuccess: () -> Unit)
}

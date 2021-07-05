package com.architectcoders.data.source

interface BiometricDataSource {
    fun setBiometricAuthentication(onFail: () -> Unit = {}, onSuccess: () -> Unit)
}

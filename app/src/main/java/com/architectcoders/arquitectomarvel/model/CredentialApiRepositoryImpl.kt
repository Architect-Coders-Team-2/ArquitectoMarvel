package com.architectcoders.arquitectomarvel.model

import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.module.data.CredentialsApiRepository

class CredentialApiRepositoryImpl : CredentialsApiRepository {
    override val currentTime: Long
        get() = System.currentTimeMillis()
    override val publicKey: String
        get() = BuildConfig.MARVEL_API_KEY
    override val privateKey: String
        get() = BuildConfig.MARVEL_PRIVATE_KEY
    override val hash: String
        get() = "$currentTime$privateKey$publicKey".md5
}
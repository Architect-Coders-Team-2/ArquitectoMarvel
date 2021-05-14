package com.architectcoders.arquitectomarvel.data

import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.module.data.sources.CredentialsSource
import java.math.BigInteger
import java.security.MessageDigest

class CredentialsSourceImpl : CredentialsSource {

    override val currentTime: Long
        get() = System.currentTimeMillis()

    override val publicKey: String
        get() = BuildConfig.MARVEL_API_KEY

    override val privateKey: String
        get() = BuildConfig.MARVEL_PRIVATE_KEY

    override val hash: String
        get() = "$currentTime$privateKey$publicKey".md5

    private val String.md5: String
        get() {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
        }
}
package com.architectcoders.arquitectomarvel.fakeDataSources

import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.ui.common.md5
import com.architectcoders.data.source.CredentialsDataSource
import javax.inject.Inject

class FakeCredentialsDataSource @Inject constructor() : CredentialsDataSource {
    override val timeStamp: Long
        get() = System.currentTimeMillis()
    override val publicKey: String
        get() = BuildConfig.MARVEL_API_KEY
    override val privateKey: String
        get() = BuildConfig.MARVEL_PRIVATE_KEY
    override val hash: String
        get() = "$timeStamp$privateKey$publicKey".md5
}

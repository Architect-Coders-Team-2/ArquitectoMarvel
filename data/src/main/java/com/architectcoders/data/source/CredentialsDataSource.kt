package com.architectcoders.data.source

interface CredentialsDataSource {
    val timeStamp: Long
    val publicKey: String
    val privateKey: String
    val hash: String
}

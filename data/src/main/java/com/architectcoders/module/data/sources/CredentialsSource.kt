package com.architectcoders.module.data.sources

interface CredentialsSource {
    val currentTime: Long
    val publicKey: String
    val privateKey: String
    val hash: String
}
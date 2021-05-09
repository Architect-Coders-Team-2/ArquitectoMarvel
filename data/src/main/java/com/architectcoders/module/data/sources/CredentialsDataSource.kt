package com.architectcoders.module.data

interface CredentialsSource {
    val currentTime: Long
    val publicKey: String
    val privateKey: String
    val hash: String
}
package com.architectcoders.data.source

import com.architectcoders.domain.characters.Characters
import com.architectcoders.domain.comics.Comic

interface RemoteDataSource {
    suspend fun getCharacters(
        ts: Long,
        publicKey: String,
        hash: String,
        offset: Int
    ): Characters

    suspend fun getCharacterById(
        characterId: Int,
        ts: Long,
        publicKey: String,
        hash: String
    ): Characters

    suspend fun getComics(
        characterId: Int,
        ts: Long,
        publicKey: String,
        hash: String
    ): Comic?
}


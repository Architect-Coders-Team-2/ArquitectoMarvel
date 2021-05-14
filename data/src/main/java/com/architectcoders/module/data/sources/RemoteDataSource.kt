package com.architectcoders.module.data.sources

import com.architectcoders.module.domain.models.Characters.Characters
import com.architectcoders.module.domain.models.Comics.Comics

interface RemoteDataSource {

    val credentialsSource: CredentialsSource

    suspend fun getCharacters(
        offset: Int
    ): Characters

    suspend fun getComics(
        characterId: Int
    ): Comics
}
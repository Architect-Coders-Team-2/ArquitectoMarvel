package com.architectcoders.module.data.sources

import com.architectcoders.module.data.CredentialsSource
import com.architectcoders.module.domain.remote_models.Characters.Characters
import com.architectcoders.module.domain.remote_models.Comics.Comics

interface RemoteDataSource {

    val credentialsSource: CredentialsSource

    suspend fun getCharacters(
        offset: Int
    ): Characters

    suspend fun getComics(
        characterId: Int
    ): Comics
}
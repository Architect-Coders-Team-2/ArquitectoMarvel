package com.architectcoders.data.source

import com.architectcoders.domain.comics.ComicsPayload
import com.architectcoders.domain.heros.HerosPayload

interface RemoteDataSource {
    val credentialsDataSource: CredentialsDataSource
    suspend fun getRemoteCharacters(offset: Int): HerosPayload
    suspend fun getRemoteCharacterById(characterId: Int): HerosPayload
    suspend fun getRemoteComics(characterId: Int): ComicsPayload?
}

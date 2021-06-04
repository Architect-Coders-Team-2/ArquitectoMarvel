package com.architectcoders.data.source

import com.architectcoders.domain.characters.HerosPayload
import com.architectcoders.domain.comics.Comic

interface RemoteDataSource {
    val credentialsDataSource: CredentialsDataSource
    suspend fun getRemoteCharacters(offset: Int): HerosPayload
    suspend fun getRemoteCharacterById(characterId: Int): HerosPayload
    suspend fun getRemoteComics(characterId: Int): Comic?
}

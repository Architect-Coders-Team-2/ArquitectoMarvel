package com.architectcoders.data.source

import com.architectcoders.domain.characters.Characters
import com.architectcoders.domain.comics.Comic

interface RemoteDataSource {
    val credentialsDataSource: CredentialsDataSource
    suspend fun getRemoteCharacters(offset: Int): Characters
    suspend fun getRemoteCharacterById(characterId: Int): Characters
    suspend fun getRemoteComics(characterId: Int): Comic
}

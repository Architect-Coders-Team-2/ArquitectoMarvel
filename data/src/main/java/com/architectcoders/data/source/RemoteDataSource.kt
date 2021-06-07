package com.architectcoders.data.source

import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.ComicsPayload

interface RemoteDataSource {
    val credentialsDataSource: CredentialsDataSource
    suspend fun getRemoteCharacters(offset: Int): CharactersPayload
    suspend fun getRemoteCharacterById(characterId: Int): CharactersPayload
    suspend fun getRemoteComics(characterId: Int): ComicsPayload?
}

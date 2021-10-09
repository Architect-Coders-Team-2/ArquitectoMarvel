package com.architectcoders.arquitectomarvel.fakeDataSources

import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.ComicsPayload
import mockedCharactersPayload
import mockedComicsPayload
import javax.inject.Inject

class FakeRemoteDataSource @Inject constructor(override val credentialsDataSource: CredentialsDataSource) :
    RemoteDataSource {
    override suspend fun getRemoteCharacters(offset: Int): CharactersPayload =
        mockedCharactersPayload

    override suspend fun getRemoteCharacterById(characterId: Int): CharactersPayload =
        mockedCharactersPayload

    override suspend fun getRemoteComics(characterId: Int): ComicsPayload = mockedComicsPayload
}

package com.architectcoders.arquitectomarvel.data.server

import com.architectcoders.arquitectomarvel.data.server.uiEntities.character.toLocalCharactersPayload
import com.architectcoders.arquitectomarvel.data.server.uiEntities.comics.toLocalComicsPayload
import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.ComicsPayload

class RetrofitDataSource(
    override val credentialsDataSource: CredentialsDataSource,
) : RemoteDataSource {

    private val marvelApi = MarvelApiRest.service

    override suspend fun getRemoteCharacters(offset: Int): CharactersPayload =
        marvelApi.getCharacters(
            credentialsDataSource.timeStamp,
            credentialsDataSource.publicKey,
            credentialsDataSource.hash,
            offset
        ).toLocalCharactersPayload

    override suspend fun getRemoteCharacterById(characterId: Int): CharactersPayload =
        marvelApi.getCharacterById(
            characterId,
            credentialsDataSource.timeStamp,
            credentialsDataSource.publicKey,
            credentialsDataSource.hash
        ).toLocalCharactersPayload

    override suspend fun getRemoteComics(characterId: Int): ComicsPayload? =
        marvelApi.getComics(
            characterId,
            credentialsDataSource.timeStamp,
            credentialsDataSource.publicKey,
            credentialsDataSource.hash
        )?.toLocalComicsPayload
}

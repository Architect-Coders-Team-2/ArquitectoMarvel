package com.architectcoders.arquitectomarvel.data.server

import com.architectcoders.arquitectomarvel.data.server.uiEntities.comics.toLocalComicsPayload
import com.architectcoders.arquitectomarvel.data.server.uiEntities.heros.toLocalHerosPayload
import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.comics.ComicsPayload
import com.architectcoders.domain.heros.HerosPayload

class MarvelDataSource(
    override val credentialsDataSource: CredentialsDataSource,
) : RemoteDataSource {

    private val marvelApi = MarvelApiRest.service

    private val marvelCredentialDataSource: MarvelCredentialDataSource =
        credentialsDataSource as MarvelCredentialDataSource

    override suspend fun getRemoteCharacters(offset: Int): HerosPayload =
        marvelApi.getCharacters(
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash,
            offset
        ).toLocalHerosPayload

    override suspend fun getRemoteCharacterById(characterId: Int): HerosPayload =
        marvelApi.getCharacterById(
            characterId,
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash
        ).toLocalHerosPayload

    override suspend fun getRemoteComics(characterId: Int): ComicsPayload? =
        marvelApi.getComics(
            characterId,
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash
        )?.toLocalComicsPayload
}

package com.architectcoders.arquitectomarvel.data.server

import com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters.toLocalCharacters
import com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelComics.toLocalComic
import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.characters.Characters
import com.architectcoders.domain.comics.Comic

class MarvelDataSource(
    override val credentialsDataSource: CredentialsDataSource,
) : RemoteDataSource {

    private val marvelApi = MarvelApiRest.service

    private val marvelCredentialDataSource: MarvelCredentialDataSource =
        credentialsDataSource as MarvelCredentialDataSource

    override suspend fun getRemoteCharacters(offset: Int): Characters =
        marvelApi.getCharacters(
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash,
            offset
        ).toLocalCharacters

    override suspend fun getRemoteCharacterById(characterId: Int): Characters =
        marvelApi.getCharacterById(
            characterId,
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash
        ).toLocalCharacters

    override suspend fun getRemoteComics(characterId: Int): Comic? =
        marvelApi.getComics(
            characterId,
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash
        )?.toLocalComic
}

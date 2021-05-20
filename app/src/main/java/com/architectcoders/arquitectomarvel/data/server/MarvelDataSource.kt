package com.architectcoders.arquitectomarvel.data.server

import com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters.toLocalCharacters
import com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelComics.toLocalComic
import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.RemoteDataSource

class MarvelDataSource(override val credentialsDataSource: CredentialsDataSource) :
    RemoteDataSource {

    private val marvelApi = MarvelApiRest.service

    private val marvelCredentialDataSource: MarvelCredentialDataSource =
        credentialsDataSource as MarvelCredentialDataSource

    override suspend fun getRemoteCharacters(vararg param: Any): Any =
        marvelApi.getCharacters(
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash,
            param.first() as Int
        ).toLocalCharacters

    override suspend fun getRemoteCharacterById(vararg param: Any): Any =
        marvelApi.getCharacterById(
            param.first() as Int,
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash
        ).toLocalCharacters

    override suspend fun getRemoteComics(vararg param: Any): Any? =
        marvelApi.getComics(
            param.first() as Int,
            marvelCredentialDataSource.timeStamp,
            marvelCredentialDataSource.publicKey,
            marvelCredentialDataSource.hash
        )?.toLocalComic
}

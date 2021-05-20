package com.architectcoders.arquitectomarvel.data.remote

import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.toCharactersDomain
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.toComicsDomain
import com.architectcoders.module.data.sources.CredentialsSource
import com.architectcoders.module.data.sources.RemoteDataSource
import com.architectcoders.module.domain.models.characters.Characters
import com.architectcoders.module.domain.models.comics.Comics

class RetrofitDataSource(private val credentials: CredentialsSource) : RemoteDataSource {

    private val api = MarvelApiRest.service

    override val credentialsSource: CredentialsSource
        get() = credentials

    override suspend fun getCharacters(
        offset: Int
    ): Characters {
        return api.getCharacters(
            ts = credentials.currentTime,
            apikey = credentials.publicKey,
            hash = credentials.hash,
            offset = offset
        ).toCharactersDomain()
    }

    override suspend fun getComics(
        characterId: Int
    ): Comics {
        return api.getComics(
            characterId,
            credentialsSource.currentTime,
            credentialsSource.publicKey,
            credentialsSource.hash
        ).toComicsDomain()
    }

}
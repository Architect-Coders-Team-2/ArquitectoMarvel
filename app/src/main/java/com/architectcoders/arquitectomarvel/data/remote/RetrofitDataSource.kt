package com.architectcoders.arquitectomarvel.data.remote

import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.toCharactersModel
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.toComicsDomain
import com.architectcoders.module.data.CredentialsSource
import com.architectcoders.module.data.sources.RemoteDataSource
import com.architectcoders.module.domain.remote_models.Characters.Characters
import com.architectcoders.module.domain.remote_models.Comics.Comics

class RetrofitDataSource(private val credentials: CredentialsSource) : RemoteDataSource {

    val api = MarvelApiRest.service

    override suspend fun getCharacters(
        credentialsSource: CredentialsSource,
        offset: Int
    ): Characters {
        return api.getCharacters(
            ts = credentials.currentTime,
            apikey = credentials.publicKey,
            hash = credentials.hash,
            offset = offset
        ).toCharactersModel()
    }

    override suspend fun getComics(
        credentialsSource: CredentialsSource,
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
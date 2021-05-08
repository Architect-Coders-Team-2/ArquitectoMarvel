package com.architectcoders.arquitectomarvel.model

import com.architectcoders.arquitectomarvel.model.characters.toCharactersModel
import com.architectcoders.arquitectomarvel.model.comics.toComicsDomain
import com.architectcoders.module.data.CredentialsApiRepository
import com.architectcoders.module.data.RemoteDataSource
import com.architectcoders.module.domain.remote_models.Characters.Characters
import com.architectcoders.module.domain.remote_models.Comics.Comics

class RetrofitDataSource(private val credentials: CredentialsApiRepository) : RemoteDataSource {

    val api = MarvelApiRest.service

    override suspend fun getCharacters(
        credentialsApiRepository: CredentialsApiRepository,
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
        credentialsApiRepository: CredentialsApiRepository,
        characterId: Int
    ): Comics {
        return api.getComics(
            characterId,
            credentialsApiRepository.currentTime,
            credentialsApiRepository.publicKey,
            credentialsApiRepository.hash
        ).toComicsDomain()
    }

}
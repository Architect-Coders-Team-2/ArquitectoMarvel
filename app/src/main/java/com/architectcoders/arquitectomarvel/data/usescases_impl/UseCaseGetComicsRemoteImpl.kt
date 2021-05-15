package com.architectcoders.arquitectomarvel.data.usescases_impl

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.models.Comics.Comics
import com.architectcoders.module.usescases.UseCaseGetComicsRemote
import javax.inject.Inject

class UseCaseGetComicsRemoteImpl @Inject constructor(private val marvelRepository: MarvelRepository) :
    UseCaseGetComicsRemote {

    override suspend fun invoke(characterId: Int): Comics {
        return marvelRepository.getComicsFromCharacterRemote(characterId)
    }
}
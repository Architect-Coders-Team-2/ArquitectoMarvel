package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.models.Comics.Comics

class UseCaseGetComicsRemote(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(characterId: Int): Comics {
        return marvelRepository.getComicsFromCharacterRemote(characterId)
    }
}
package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.models.Characters.Characters as CharactersCharacters

class UseCaseGetCharactersRemote(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(offset: Int): CharactersCharacters {
        return marvelRepository.getCharactersRemote(offset)
    }
}
package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.models.characters.ResultCharacters

class UseCaseDeleteFavoriteCharacter(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(character: ResultCharacters) {
        return marvelRepository.deleteFavoriteCharacters(character)
    }
}
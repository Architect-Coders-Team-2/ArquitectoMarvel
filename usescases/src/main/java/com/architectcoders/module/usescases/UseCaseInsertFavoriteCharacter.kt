package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.models.characters.ResultCharacters

class UseCaseInsertFavoriteCharacter(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(favouriteCharacter: ResultCharacters) {
        return marvelRepository.insertFavoriteCharacter(favouriteCharacter)
    }
}
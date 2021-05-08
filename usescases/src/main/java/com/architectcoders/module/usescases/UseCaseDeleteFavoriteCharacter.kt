package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.remote_models.Characters.Result

class UseCaseDeleteFavoriteCharacter(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(character: Result) {
        return marvelRepository.deleteFavoriteCharacters(character)
    }
}
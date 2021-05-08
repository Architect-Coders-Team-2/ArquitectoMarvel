package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.remote_models.Characters.Result

class UseCaseInsertFavoriteCharacter(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(favouriteCharacter: Result) {
        return marvelRepository.insertFavoriteCharacter(favouriteCharacter)
    }
}
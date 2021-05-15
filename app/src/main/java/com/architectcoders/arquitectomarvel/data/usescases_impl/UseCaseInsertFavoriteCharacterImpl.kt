package com.architectcoders.arquitectomarvel.data.usescases_impl

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.models.Characters.ResultCharacters
import com.architectcoders.module.usescases.UseCaseInsertFavoriteCharacter
import javax.inject.Inject

class UseCaseInsertFavoriteCharacterImpl @Inject constructor(private val marvelRepository: MarvelRepository):
    UseCaseInsertFavoriteCharacter {

    override suspend fun invoke(favouriteCharacter: ResultCharacters) {
        return marvelRepository.insertFavoriteCharacter(favouriteCharacter)
    }
}
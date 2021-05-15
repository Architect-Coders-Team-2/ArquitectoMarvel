package com.architectcoders.arquitectomarvel.data.usescases_impl

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.models.Characters.ResultCharacters
import com.architectcoders.module.usescases.UseCaseDeleteFavoriteCharacter
import javax.inject.Inject

class UseCaseDeleteFavoriteCharacterImpl @Inject constructor(private val marvelRepository: MarvelRepository):
    UseCaseDeleteFavoriteCharacter {

    override suspend fun invoke(character: ResultCharacters) {
        return marvelRepository.deleteFavoriteCharacters(character)
    }
}
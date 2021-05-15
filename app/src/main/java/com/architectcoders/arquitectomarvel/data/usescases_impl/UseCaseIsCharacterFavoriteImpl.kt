package com.architectcoders.arquitectomarvel.data.usescases_impl

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.usescases.UseCaseIsCharacterFavorite
import javax.inject.Inject

class UseCaseIsCharacterFavoriteImpl @Inject constructor(private val marvelRepository: MarvelRepository):
    UseCaseIsCharacterFavorite {

    override suspend fun invoke(characterId: Int): Boolean {
        return marvelRepository.isCharacterFavorite(characterId)
    }
}
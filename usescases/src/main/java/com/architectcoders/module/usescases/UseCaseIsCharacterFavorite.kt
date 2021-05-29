package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository

class UseCaseIsCharacterFavorite(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(characterId: Int): Boolean {
        return marvelRepository.isCharacterFavorite(characterId)
    }
}
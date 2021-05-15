package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Result

class InsertFavoriteHero(private val characterRepository: MarvelRepository) {
    suspend fun invoke(character: Result) = characterRepository.insertFavoriteHero(character)
}
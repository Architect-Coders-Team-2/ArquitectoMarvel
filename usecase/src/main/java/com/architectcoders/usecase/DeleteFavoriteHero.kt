package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Result

class DeleteFavoriteHero(private val characterRepository: MarvelRepository) {
    suspend fun invoke(character: Result) = characterRepository.deleteFavoriteHero(character)
}
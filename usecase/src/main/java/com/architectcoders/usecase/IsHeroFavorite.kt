package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository

class IsHeroFavorite(private val characterRepository: MarvelRepository) {
    suspend fun invoke(id: Int): Boolean = characterRepository.isHeroFavorite(id)
}
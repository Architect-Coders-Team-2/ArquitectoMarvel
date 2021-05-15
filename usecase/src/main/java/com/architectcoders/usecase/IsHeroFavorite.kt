package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository

class IsHeroFavorite(private val marvelRepository: MarvelRepository) {
    suspend fun invoke(id: Int): Boolean = marvelRepository.isHeroFavorite(id)
}
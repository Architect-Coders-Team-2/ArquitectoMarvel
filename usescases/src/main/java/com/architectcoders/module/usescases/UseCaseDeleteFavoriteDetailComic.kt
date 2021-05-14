package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.models.Comics.DetailedComic

class UseCaseDeleteFavoriteDetailComic(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(comic: DetailedComic) {
        return marvelRepository.deleteFavoriteDetailedComic(comic)
    }
}
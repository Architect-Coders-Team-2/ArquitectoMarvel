package com.architectcoders.module.usescases

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.domain.local_models.DetailedComic

class UseCaseInsertFavoriteComic(private val marvelRepository: MarvelRepository) {

    suspend fun invoke(detailedComic: DetailedComic) {
        return marvelRepository.insertFavoriteComic(detailedComic)
    }
}
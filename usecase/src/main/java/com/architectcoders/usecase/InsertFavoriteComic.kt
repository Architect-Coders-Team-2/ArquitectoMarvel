package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.Result

class InsertFavoriteComic(private val marvelRepository: MarvelRepository) {
    suspend fun invoke(comic: Result) = marvelRepository.insertFavoriteComic(comic)
}
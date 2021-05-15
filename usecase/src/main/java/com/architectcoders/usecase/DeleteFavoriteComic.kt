package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.Result

class DeleteFavoriteComic(private val marvelRepository: MarvelRepository) {
    suspend fun invoke(comic: Result) = marvelRepository.deleteFavoriteComic(comic)
}
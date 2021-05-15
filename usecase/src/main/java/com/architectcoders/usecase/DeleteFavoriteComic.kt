package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.Result

class DeleteFavoriteComic(private val characterRepository: MarvelRepository) {
    suspend fun invoke(comic: Result) = characterRepository.deleteFavoriteComic(comic)
}
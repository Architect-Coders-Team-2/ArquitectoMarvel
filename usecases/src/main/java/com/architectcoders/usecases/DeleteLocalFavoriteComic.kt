package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.Result as ComicResult

class DeleteLocalFavoriteComic(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<ComicResult, Unit> {
    override suspend fun invoke(param: ComicResult) =
        marvelRepository.deleteLocalFavoriteComic(param)
}

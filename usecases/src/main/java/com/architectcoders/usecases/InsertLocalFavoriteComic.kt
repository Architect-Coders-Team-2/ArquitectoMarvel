package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.Result as ComicResult

class InsertLocalFavoriteComic(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<ComicResult, Unit> {
    override suspend fun invoke(param: ComicResult) =
        marvelRepository.insertLocalFavoriteComic(param)
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comic.Comic

class InsertLocalFavoriteComic(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Comic, Unit> {
    override suspend fun invoke(param: Comic) =
        marvelRepository.insertLocalFavoriteComic(param)
}

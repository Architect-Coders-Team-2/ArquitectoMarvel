package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class InsertFavoriteComic(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Any, Unit> {
    override suspend fun invoke(vararg param: Any) =
        marvelRepository.insertFavoriteComic(*param)
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class IsCharacterFavorite(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, Boolean> {
    override suspend fun invoke(param: Int): Boolean =
        marvelRepository.isLocalCharacterFavorite(param)
}

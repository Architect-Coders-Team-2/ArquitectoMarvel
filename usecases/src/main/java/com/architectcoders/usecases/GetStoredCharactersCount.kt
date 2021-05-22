package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetStoredCharactersCount(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Unit, Int> {
    override suspend fun invoke(param: Unit): Int =
        marvelRepository.getStoredCharactersCount()
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetLocalCharactersCount(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Unit, Int> {
    override suspend fun invoke(param: Unit): Int =
        marvelRepository.getLocalCharactersCount()
}

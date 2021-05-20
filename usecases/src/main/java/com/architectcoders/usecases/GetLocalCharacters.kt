package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetLocalCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Unit, Any> {
    override suspend fun invoke(vararg param: Unit): Any? =
        marvelRepository.getLocalCharacters()
}

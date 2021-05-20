package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetRemoteCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Any, Any> {
    override suspend fun invoke(vararg param: Any): Any =
        marvelRepository.getRemoteCharacters(*param)
}

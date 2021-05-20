package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetComicsFromCharacterId(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Any, Any> {
    override suspend fun invoke(vararg param: Any): Any? =
        marvelRepository.getComicsFromCharacterId(*param)
}

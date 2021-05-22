package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Characters

class GetCharacterById(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, Any> {
    override suspend fun invoke(param: Int): Characters =
        marvelRepository.getRemoteCharacterById(param)
}

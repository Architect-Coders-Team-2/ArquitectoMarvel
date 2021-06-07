package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.character.Character

class GetLocalCharacterById(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, Any> {
    override suspend fun invoke(param: Int): Character =
        marvelRepository.getLocalCharacterById(param)
}

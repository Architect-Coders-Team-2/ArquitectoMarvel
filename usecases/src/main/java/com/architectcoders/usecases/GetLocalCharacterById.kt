package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Hero as CharacterResult

class GetLocalCharacterById(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, Any> {
    override suspend fun invoke(param: Int): CharacterResult =
        marvelRepository.getLocalCharacterById(param)
}

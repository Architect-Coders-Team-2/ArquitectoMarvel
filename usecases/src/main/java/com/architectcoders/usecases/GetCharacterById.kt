package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Result as CharacterResult

class GetCharacterById(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, Any> {
    override suspend fun invoke(param: Int): CharacterResult =
        marvelRepository.getRemoteCharacterById(param)
}

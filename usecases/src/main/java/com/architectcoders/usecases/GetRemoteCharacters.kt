package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.character.CharactersPayload

class GetRemoteCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, CharactersPayload> {
    override suspend fun invoke(param: Int): CharactersPayload =
        marvelRepository.getRemoteCharacters(param)
}

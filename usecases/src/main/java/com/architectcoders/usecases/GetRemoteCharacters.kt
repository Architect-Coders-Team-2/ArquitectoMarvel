package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.character.CharactersPayload
import javax.inject.Inject

class GetRemoteCharacters @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Int, CharactersPayload> {
    override suspend fun invoke(param: Int): CharactersPayload =
        marvelRepository.getRemoteCharacters(param)
}

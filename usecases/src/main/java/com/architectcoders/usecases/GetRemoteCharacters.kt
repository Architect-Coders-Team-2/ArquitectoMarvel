package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.heros.HerosPayload

class GetRemoteCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, HerosPayload> {
    override suspend fun invoke(param: Int): HerosPayload =
        marvelRepository.getRemoteCharacters(param)
}

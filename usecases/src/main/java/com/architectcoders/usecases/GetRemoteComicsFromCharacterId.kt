package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.ComicsPayload

class GetRemoteComicsFromCharacterId(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, ComicsPayload> {
    override suspend fun invoke(param: Int): ComicsPayload? =
        marvelRepository.getRemoteComicsFromCharacterId(param)
}

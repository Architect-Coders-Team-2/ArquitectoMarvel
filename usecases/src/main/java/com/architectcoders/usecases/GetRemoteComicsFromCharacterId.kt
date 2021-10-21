package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comic.ComicsPayload
import javax.inject.Inject

class GetRemoteComicsFromCharacterId @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Int, ComicsPayload> {
    override suspend fun invoke(param: Int): ComicsPayload? =
        marvelRepository.getRemoteComicsFromCharacterId(param)
}

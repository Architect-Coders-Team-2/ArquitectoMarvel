package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.Comic

class GetRemoteComicsFromCharacterId(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, Comic> {
    override suspend fun invoke(param: Int): Comic? =
        marvelRepository.getRemoteComicsFromCharacterId(param)
}

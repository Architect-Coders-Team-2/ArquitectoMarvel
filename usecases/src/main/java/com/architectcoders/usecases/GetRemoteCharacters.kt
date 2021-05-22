package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Characters

class GetRemoteCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Int, Characters> {
    override suspend fun invoke(param: Int): Characters =
        marvelRepository.getRemoteCharacters(param)
}

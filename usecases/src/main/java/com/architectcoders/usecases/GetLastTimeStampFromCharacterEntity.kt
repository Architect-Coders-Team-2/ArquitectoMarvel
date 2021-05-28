package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetLastTimeStampFromCharacterEntity(private val marvelRepository: MarvelRepository) : InvokeUseCase<Unit, Long> {
    override suspend fun invoke(param: Unit): Long? =
        marvelRepository.getLastTimeStampFromCharacterEntity()
}

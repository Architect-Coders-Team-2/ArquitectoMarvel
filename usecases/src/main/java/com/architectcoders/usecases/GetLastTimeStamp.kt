package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetLastTimeStamp(private val marvelRepository: MarvelRepository) : InvokeUseCase<Unit, Long> {
    override suspend fun invoke(param: Unit): Long? =
        marvelRepository.getLastTimeStamp()
}

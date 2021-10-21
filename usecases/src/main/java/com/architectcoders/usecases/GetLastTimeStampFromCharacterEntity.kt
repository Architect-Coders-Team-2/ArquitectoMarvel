package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class GetLastTimeStampFromCharacterEntity @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Unit, Long> {
    override suspend fun invoke(param: Unit): Long? =
        marvelRepository.getLastTimeStampFromCharacterEntity()
}

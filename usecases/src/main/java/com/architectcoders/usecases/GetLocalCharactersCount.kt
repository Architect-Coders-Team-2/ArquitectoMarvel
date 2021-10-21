package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class GetLocalCharactersCount @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Unit, Int> {
    override suspend fun invoke(param: Unit): Int =
        marvelRepository.getLocalCharactersCount()
}

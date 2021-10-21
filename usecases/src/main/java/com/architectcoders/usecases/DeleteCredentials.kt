package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class DeleteCredentials @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Unit, Unit> {
    override suspend fun invoke(param: Unit) = marvelRepository.deleteCredentials()
}

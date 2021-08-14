package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class IsPasswordAlreadyStored(private val marvelRepository: MarvelRepository) : InvokeUseCase<Unit, Boolean> {
    override suspend fun invoke(param: Unit): Boolean =
        marvelRepository.isPasswordAlreadyStored()
}

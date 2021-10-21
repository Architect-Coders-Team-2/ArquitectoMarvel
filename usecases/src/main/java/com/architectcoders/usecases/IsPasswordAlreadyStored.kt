package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class IsPasswordAlreadyStored @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Unit, Boolean> {
    override suspend fun invoke(param: Unit): Boolean =
        marvelRepository.isPasswordAlreadyStored()
}

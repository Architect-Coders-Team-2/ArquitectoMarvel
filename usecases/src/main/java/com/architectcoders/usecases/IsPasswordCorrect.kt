package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class IsPasswordCorrect @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<String, Boolean> {
    override suspend fun invoke(param: String): Boolean =
        marvelRepository.isPasswordCorrect(param)
}

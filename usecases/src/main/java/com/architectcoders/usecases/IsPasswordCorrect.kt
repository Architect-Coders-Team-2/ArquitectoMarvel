package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class IsPasswordCorrect(private val marvelRepository: MarvelRepository) : InvokeUseCase<String, Boolean> {
    override suspend fun invoke(param: String): Boolean =
        marvelRepository.isPasswordCorrect(param)
}

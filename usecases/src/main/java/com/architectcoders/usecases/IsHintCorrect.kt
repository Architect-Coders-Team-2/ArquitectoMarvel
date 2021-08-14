package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class IsHintCorrect(private val marvelRepository: MarvelRepository) : InvokeUseCase<String, Boolean> {
    override suspend fun invoke(param: String): Boolean =
        marvelRepository.isRecoveryHintCorrect(param)
}

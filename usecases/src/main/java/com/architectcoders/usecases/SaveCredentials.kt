package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class SaveCredentials(private val marvelRepository: MarvelRepository) {
    suspend fun invoke(password: Int, recoveryHint: String): Unit =
        marvelRepository.saveCredentials(password, recoveryHint)
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class SaveCredentials @Inject constructor(
    private val marvelRepository: MarvelRepository
) {
    suspend fun invoke(password: String, recoveryHint: String): Unit =
        marvelRepository.saveCredentials(password, recoveryHint)
}

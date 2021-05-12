package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository

class IsCharacterFavorite(private val characterRepository: CharacterRepository) {
    suspend fun invoke(id: Int): Boolean = characterRepository.isCharacterFavorite(id)
}

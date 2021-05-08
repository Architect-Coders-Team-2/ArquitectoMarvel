package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Characters

class GetCharacterById(private val characterRepository: CharacterRepository) {
    suspend fun invoke(id: Int): Characters = characterRepository.getCharacterById(id)
}

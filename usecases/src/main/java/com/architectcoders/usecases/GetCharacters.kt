package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Characters

class GetCharacters(private val characterRepository: CharacterRepository) {
    suspend fun invoke(offset: Int): Characters = characterRepository.getCharactersRemote(offset)
}

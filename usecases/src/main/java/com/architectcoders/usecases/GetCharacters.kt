package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Characters

class GetCharacters(private val characterRepository: CharacterRepository) {
    suspend fun invoke(offset: Int, ts: Long, hash: String): Characters =
        characterRepository.getCharactersRemote(offset, ts, hash)
}

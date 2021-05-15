package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Characters

class GetCharacters(private val characterRepository: MarvelRepository) {
    suspend fun invoke(offset: Int, ts: Long, hash: String): Characters =
        characterRepository.getCharactersRemote(offset, ts, hash)
}
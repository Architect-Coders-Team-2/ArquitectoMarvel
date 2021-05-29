package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Characters

class GetCharacters(private val marvelRepository: MarvelRepository) {
    suspend fun invoke(offset: Int, ts: Long, hash: String): Characters =
        marvelRepository.getCharactersRemote(offset, ts, hash)
}
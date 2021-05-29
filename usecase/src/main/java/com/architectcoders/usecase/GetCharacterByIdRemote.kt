package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Characters

class GetCharacterByIdRemote(private val marvelRepository: MarvelRepository) {
    suspend fun invoke(id: Int, ts: Long, hash: String): Characters =
        marvelRepository.getCharacterByIdRemote(id, ts, hash)
}
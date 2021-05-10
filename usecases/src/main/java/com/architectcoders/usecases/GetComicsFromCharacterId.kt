package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.comics.Comic

class GetComicsFromCharacterId(private val characterRepository: CharacterRepository) {
    suspend fun invoke(id: Int, ts: Long, hash: String): Comic? =
        characterRepository.getComicsFromCharacterId(id, ts, hash)
}

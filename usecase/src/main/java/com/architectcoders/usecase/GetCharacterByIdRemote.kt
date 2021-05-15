package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository

class GetCharacterByIdRemote(private val characterRepository: MarvelRepository) {
    suspend fun invoke(id: Int, ts: Long, hash: String): Characters =
        characterRepository.getComicsFromHeroIdRemote(id, ts, hash)
}
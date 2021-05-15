package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.Comic


class GetComicsFromHeroIdRemote(private val characterRepository: MarvelRepository) {
    suspend fun invoke(id: Int, ts: Long, hash: String): Comic? =
        characterRepository.getComicsFromHeroIdRemote(id, ts, hash)
}
package com.architectcoders.usecase

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.comics.Comic


class GetComicsFromHeroIdRemote(private val marvelRepository: MarvelRepository) {
    suspend fun invoke(id: Int, ts: Long, hash: String): Comic? =
        marvelRepository.getComicsFromHeroIdRemote(id, ts, hash)
}
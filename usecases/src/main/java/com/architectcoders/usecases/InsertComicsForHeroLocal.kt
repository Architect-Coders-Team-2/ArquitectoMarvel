package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class InsertComicsForHeroLocal(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Map<String, Any>, Unit> {
    override suspend fun invoke(param: Map<String, Any>): Unit =
        marvelRepository.fetchComicsForHero(param)
}

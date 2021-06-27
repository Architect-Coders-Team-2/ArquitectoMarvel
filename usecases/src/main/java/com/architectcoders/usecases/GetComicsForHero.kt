package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetComicsForHero(private val marvelRepository: MarvelRepository) {
    fun invoke(idHero: Int): Any =
        marvelRepository.getComicsForHero(idHero)
}
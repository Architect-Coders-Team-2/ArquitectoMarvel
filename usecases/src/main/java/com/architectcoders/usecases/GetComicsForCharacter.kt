package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetComicsForCharacter(private val marvelRepository: MarvelRepository) {
    fun invoke(characterId: Int): Any =
        marvelRepository.getComicsForCharacter(characterId)
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class GetComicsForCharacter @Inject constructor(
    private val marvelRepository: MarvelRepository
) {
    fun invoke(characterId: Int): Any =
        marvelRepository.getComicsForCharacter(characterId)
}

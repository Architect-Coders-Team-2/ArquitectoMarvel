package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.character.Character

class InsertAllLocalCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<List<Character>, Unit> {
    override suspend fun invoke(param: List<Character>) =
        marvelRepository.insertAllLocalCharacters(param)
}

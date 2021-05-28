package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Result as CharacterResult

class InsertAllLocalCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<List<CharacterResult>, Unit> {
    override suspend fun invoke(param: List<CharacterResult>) =
        marvelRepository.insertAllLocalCharacters(param)
}

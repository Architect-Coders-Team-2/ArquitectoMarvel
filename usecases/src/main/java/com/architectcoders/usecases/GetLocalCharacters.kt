package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Result as CharacterResult

class GetLocalCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Unit, List<CharacterResult>> {
    override suspend fun invoke(param: Unit): List<CharacterResult>? =
        marvelRepository.getLocalCharacters()
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class DeleteAllCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Unit, Unit> {
    override suspend fun invoke(param: Unit) =
        marvelRepository.deleteAllLocalCharacters()
}

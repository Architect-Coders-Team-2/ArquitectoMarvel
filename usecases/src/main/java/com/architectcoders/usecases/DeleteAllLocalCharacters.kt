package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class DeleteAllLocalCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Unit, Unit> {
    override suspend fun invoke(param: Unit) =
        marvelRepository.deleteAllLocalCharacters()
}

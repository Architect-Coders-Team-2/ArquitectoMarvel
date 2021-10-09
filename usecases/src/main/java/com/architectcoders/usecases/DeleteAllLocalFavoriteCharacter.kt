package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class DeleteAllLocalFavoriteCharacter(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Unit, Unit> {
    override suspend fun invoke(param: Unit) =
        marvelRepository.deleteAllLocalFavoriteCharacter()
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetLocalFavoriteCharacters(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Unit, Any?> {
    override suspend fun invoke(param: Unit): Any =
        marvelRepository.getLocalFavoriteCharacters()
}

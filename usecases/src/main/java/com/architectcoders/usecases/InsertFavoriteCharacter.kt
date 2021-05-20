package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class InsertFavoriteCharacter(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Any, Unit> {
    override suspend fun invoke(vararg param: Any) =
        marvelRepository.insertFavoriteCharacter(*param)
}

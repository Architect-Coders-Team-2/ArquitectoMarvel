package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class DeleteFavoriteCharacter(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Any, Unit> {
    override suspend fun invoke(vararg param: Any) =
        marvelRepository.deleteFavoriteCharacter(*param)
}

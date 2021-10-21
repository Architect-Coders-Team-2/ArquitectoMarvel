package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class GetLocalFavoriteCharacters @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Unit, Any?> {
    override suspend fun invoke(param: Unit): Any =
        marvelRepository.getLocalFavoriteCharacters()
}

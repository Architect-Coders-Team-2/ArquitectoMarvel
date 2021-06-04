package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.heros.Hero as CharacterResult

class InsertLocalFavoriteCharacter(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<CharacterResult, Unit> {
    override suspend fun invoke(param: CharacterResult) =
        marvelRepository.insertLocalFavoriteCharacter(param)
}

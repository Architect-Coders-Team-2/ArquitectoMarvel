package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Result as CharacterResult

class InsertFavoriteCharacter(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<CharacterResult, Unit> {
    override suspend fun invoke(param: CharacterResult) =
        marvelRepository.insertLocalFavoriteCharacter(param)
}

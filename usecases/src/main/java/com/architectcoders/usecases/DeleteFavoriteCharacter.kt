package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Result as CharacterResult

class DeleteFavoriteCharacter(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<CharacterResult, Unit> {
    override suspend fun invoke(param: CharacterResult) =
        marvelRepository.deleteLocalFavoriteCharacter(param)
}

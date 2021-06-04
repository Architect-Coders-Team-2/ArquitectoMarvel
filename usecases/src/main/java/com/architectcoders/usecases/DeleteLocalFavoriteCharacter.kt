package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.characters.Hero as CharacterResult

class DeleteLocalFavoriteCharacter(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<CharacterResult, Unit> {
    override suspend fun invoke(param: CharacterResult) =
        marvelRepository.deleteLocalFavoriteCharacter(param)
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.character.Character

class DeleteLocalFavoriteCharacter(private val marvelRepository: MarvelRepository) :
    InvokeUseCase<Character, Unit> {
    override suspend fun invoke(param: Character) =
        marvelRepository.deleteLocalFavoriteCharacter(param)
}

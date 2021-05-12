package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Result

class DeleteFavoriteCharacter(private val characterRepository: CharacterRepository) {
    suspend fun invoke(character: Result) = characterRepository.deleteFavoriteCharacter(character)
}

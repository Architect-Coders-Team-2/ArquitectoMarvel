package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Result

class InsertFavoriteCharacter(private val characterRepository: CharacterRepository)
    :IUseCase<Result,Any> {
   override suspend fun invoke(character: Result) = characterRepository.insertFavoriteCharacter(character)
}

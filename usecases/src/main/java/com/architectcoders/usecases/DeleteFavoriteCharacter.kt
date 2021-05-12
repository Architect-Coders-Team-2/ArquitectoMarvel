package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Characters
import com.architectcoders.domain.characters.Result

class DeleteFavoriteCharacter(private val characterRepository: CharacterRepository): IUseCase<Any,Result> {
    override suspend fun invoke(param: Result) = characterRepository.deleteFavoriteCharacter(param)
}

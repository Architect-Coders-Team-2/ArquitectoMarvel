package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository

class IsCharacterFavorite(private val characterRepository: CharacterRepository):
IUseCase<Int,Boolean>{
    override suspend fun invoke(id: Int) =
        characterRepository.isCharacterFavorite(id)

}

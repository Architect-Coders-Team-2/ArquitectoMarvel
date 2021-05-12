package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Characters

class GetCharacterById(private val characterRepository: CharacterRepository): IUseCase<Characters,Int>{
    override suspend fun invoke(param: Int): Characters =
        characterRepository.getCharacterById(param)


}

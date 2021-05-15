package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Characters

class GetCharacters(private val characterRepository: CharacterRepository)
    : IUseCase<Int,Characters>{
    override suspend fun  invoke(params: Int): Characters  =
           characterRepository.getCharactersRemote(params)
}



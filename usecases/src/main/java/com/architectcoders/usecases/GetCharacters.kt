package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Characters

class GetCharacters(private val characterRepository: CharacterRepository): IUseCase<Characters,Int>{
    override suspend fun  invoke(params: Int): Characters  =
           characterRepository.getCharactersRemote(params)




    // suspend fun invoke(offset: Int): Characters = characterRepository.getCharactersRemote(offset)
}



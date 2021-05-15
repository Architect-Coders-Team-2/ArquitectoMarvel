package com.architectcoders.arquitectomarvel.data.usescases_impl

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.usescases.UseCaseGetCharactersRemote
import javax.inject.Inject
import com.architectcoders.module.domain.models.Characters.Characters as CharactersCharacters

class UseCaseGetCharactersRemoteImpl @Inject constructor(private val marvelRepository: MarvelRepository) :
    UseCaseGetCharactersRemote {

    override suspend fun invoke(offset: Int): CharactersCharacters {
        return marvelRepository.getCharactersRemote(offset)
    }
}
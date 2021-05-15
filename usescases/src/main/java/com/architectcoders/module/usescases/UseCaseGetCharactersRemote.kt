package com.architectcoders.module.usescases

import com.architectcoders.module.domain.models.Characters.Characters as CharactersCharacters

interface UseCaseGetCharactersRemote {

    suspend fun invoke(offset: Int): CharactersCharacters
}
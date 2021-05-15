package com.architectcoders.module.usescases
import com.architectcoders.module.domain.models.Characters.ResultCharacters

interface UseCaseDeleteFavoriteCharacter {

    suspend fun invoke(character: ResultCharacters)
}
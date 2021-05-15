package com.architectcoders.module.usescases

import com.architectcoders.module.domain.models.Characters.ResultCharacters

interface UseCaseInsertFavoriteCharacter {

    suspend fun invoke(favouriteCharacter: ResultCharacters)
}
package com.architectcoders.module.data.sources

import com.architectcoders.module.domain.models.Characters.ResultCharacters

interface LocalDataSource {
    suspend fun insertFavoriteCharacter(toCharacterEntity: ResultCharacters)
    suspend fun deleteFavoriteCharacter(favouriteCharacter: ResultCharacters)
    suspend fun isCharacterFavorite(characterId: Int): Boolean
}
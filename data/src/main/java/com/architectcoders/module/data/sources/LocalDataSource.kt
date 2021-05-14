package com.architectcoders.module.data.sources

import com.architectcoders.module.domain.models.Comics.DetailedComic
import com.architectcoders.module.domain.models.Characters.ResultCharacters

interface LocalDataSource {
    suspend fun insertFavoriteCharacter(toCharacterEntity: ResultCharacters)
    suspend fun insertFavoriteDetailedComic(comic: DetailedComic)
    suspend fun deleteFavoriteCharacter(favouriteCharacter: ResultCharacters)
    suspend fun deleteFavoriteDetailedComic(comic: DetailedComic)
    suspend fun isCharacterFavorite(characterId: Int): Boolean
}
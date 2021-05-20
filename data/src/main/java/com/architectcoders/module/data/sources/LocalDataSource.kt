package com.architectcoders.module.data.sources

import com.architectcoders.module.domain.models.comics.DetailedComic
import com.architectcoders.module.domain.models.characters.ResultCharacters

interface LocalDataSource {
    suspend fun insertFavoriteCharacter(toCharacterEntity: ResultCharacters)
    suspend fun insertFavoriteDetailedComic(comic: DetailedComic)
    suspend fun deleteFavoriteCharacter(favouriteCharacter: ResultCharacters)
    suspend fun deleteFavoriteDetailedComic(comic: DetailedComic)
    suspend fun isCharacterFavorite(characterId: Int): Boolean
}
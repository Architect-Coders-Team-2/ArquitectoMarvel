package com.architectcoders.module.data.sources

import com.architectcoders.module.domain.local_models.DetailedComic
import com.architectcoders.module.domain.remote_models.Characters.Result

interface LocalDataSource {
    suspend fun insertFavoriteCharacter(toCharacterEntity: Result)
    suspend fun insertFavoriteDetailedComic(comic: DetailedComic)
    suspend fun deleteFavoriteCharacter(favouriteCharacter: Result)
    suspend fun deleteFavoriteDetailedComic(comic: DetailedComic)
    suspend fun isCharacterFavorite(characterId: Int): Boolean
}
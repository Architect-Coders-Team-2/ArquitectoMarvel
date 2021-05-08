package com.architectcoders.data.source

import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

interface LocalDataSource {
    suspend fun insertFavoriteCharacter(favouriteCharacter: CharacterResult)
    suspend fun deleteFavoriteCharacter(favouriteCharacter: CharacterResult)
    suspend fun isCharacterFavorite(characterId: Int): Boolean
    suspend fun insertFavoriteDetailedComic(comic: ComicResult)
    suspend fun deleteFavoriteDetailedComic(comic: ComicResult)
}

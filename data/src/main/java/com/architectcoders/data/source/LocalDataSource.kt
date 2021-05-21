package com.architectcoders.data.source

import com.architectcoders.domain.comics.Result as ComicResult
import com.architectcoders.domain.characters.Result as CharacterResult

interface LocalDataSource {
    suspend fun getLocalCharacters(): List<CharacterResult>?
    suspend fun isCharacterFavorite(characterId: Int): Boolean
    suspend fun insertFavoriteCharacter(favouriteCharacter: CharacterResult)
    suspend fun deleteFavoriteCharacter(favouriteCharacter: CharacterResult)
    suspend fun insertFavoriteDetailedComic(favouriteComic: ComicResult)
    suspend fun deleteFavoriteDetailedComic(favouriteComic: ComicResult)
}

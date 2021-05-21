package com.architectcoders.data.source

import com.architectcoders.domain.comics.Result as ComicResult
import com.architectcoders.domain.characters.Result as CharacterResult

interface LocalDataSource {
    suspend fun getLocalCharacters(): List<CharacterResult>?
    suspend fun isCharacterFavorite(characterId: Int): Boolean
    suspend fun insertFavoriteCharacter(favoriteCharacter: CharacterResult)
    suspend fun deleteFavoriteCharacter(favoriteCharacter: CharacterResult)
    suspend fun insertFavoriteDetailedComic(favoriteComic: ComicResult)
    suspend fun deleteFavoriteDetailedComic(favoriteComic: ComicResult)
}

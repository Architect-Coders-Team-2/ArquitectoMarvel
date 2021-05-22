package com.architectcoders.data.source

import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

interface LocalDataSource {
    suspend fun getLocalCharacters(): List<CharacterResult>?
    suspend fun getLocalCharacterById(characterId: Int): CharacterResult
    suspend fun getLastTimeStamp(): Long?
    suspend fun getStoredCharactersCount(): Int
    suspend fun isCharacterFavorite(characterId: Int): Boolean
    suspend fun insertAllCharacters(characterList: List<CharacterResult>)
    suspend fun deleteAllCharacters()
    suspend fun insertFavoriteCharacter(favoriteCharacter: CharacterResult)
    suspend fun deleteFavoriteCharacter(favoriteCharacter: CharacterResult)
    suspend fun insertFavoriteDetailedComic(favoriteComic: ComicResult)
    suspend fun deleteFavoriteDetailedComic(favoriteComic: ComicResult)
    fun getPagingSource(): Any?
}

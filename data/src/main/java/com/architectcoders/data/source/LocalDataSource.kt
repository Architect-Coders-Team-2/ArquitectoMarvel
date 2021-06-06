package com.architectcoders.data.source

import com.architectcoders.domain.character.Character as CharacterResult
import com.architectcoders.domain.comic.Comic as ComicResult

interface LocalDataSource {
    suspend fun getLocalCharacterById(characterId: Int): CharacterResult
    suspend fun getLastTimeStampFromCharacterEntity(): Long?
    suspend fun getLocalCharactersCount(): Int
    suspend fun isLocalCharacterFavorite(characterId: Int): Boolean
    suspend fun insertAllLocalCharacters(characterList: List<CharacterResult>)
    suspend fun deleteAllLocalCharacters()
    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: CharacterResult)
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: CharacterResult)
    suspend fun insertLocalFavoriteComic(favoriteComic: ComicResult)
    suspend fun deleteLocalFavoriteComic(favoriteComic: ComicResult)
    fun getPagingSourceFromCharacterEntity(): Any?
}

package com.architectcoders.data.source

import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

interface LocalDataSource {
    suspend fun getLocalCharacterById(characterId: Int): CharacterResult
    suspend fun getLastTimeStampFromCharacterEntity(): Long?
    suspend fun getLocalCharactersCount(): Int
    suspend fun isLocalCharacterFavorite(characterId: Int): Boolean
    suspend fun insertAllLocalCharacters(characterList: List<CharacterResult>)
    suspend fun deleteAllLocalCharacters()
    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: CharacterResult)
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: CharacterResult)
    suspend fun deleteLocalFavoriteComic(idHero: Int)
    fun getPagingSourceFromCharacterEntity(): Any?

    fun getComicsForHero(idHero: Int): Any
    suspend fun fetchComicsForHero(map: Map<String, Any>)
}

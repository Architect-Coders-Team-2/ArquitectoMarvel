package com.architectcoders.data.source

import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic

interface LocalDataSource {
    suspend fun getLocalCharacterById(characterId: Int): Character
    suspend fun getLastTimeStampFromCharacterEntity(): Long?
    suspend fun getLocalCharactersCount(): Int
    fun isLocalCharacterFavorite(characterId: Int): Any
    suspend fun insertAllLocalCharacters(characterList: List<Character>)
    suspend fun deleteAllLocalCharacters()
    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character)
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character)
    fun getPagingSourceFromCharacterEntity(): Any?
    fun getComicsForCharacter(characterId: Int): Any
    suspend fun fetchComicsForCharacter(map: Map<String, Any>)
}

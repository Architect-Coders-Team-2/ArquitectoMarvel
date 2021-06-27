package com.architectcoders.data.source

import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic

interface LocalDataSource {
    suspend fun getLocalCharacterById(characterId: Int): Character
    suspend fun getLastTimeStampFromCharacterEntity(): Long?
    suspend fun getLocalCharactersCount(): Int
    suspend fun isLocalCharacterFavorite(characterId: Int): Boolean
    suspend fun insertAllLocalCharacters(characterList: List<Character>)
    suspend fun deleteAllLocalCharacters()
    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character)
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character)
    fun getPagingSourceFromCharacterEntity(): Any?

    fun getComicsForHero(idHero: Int): Any
    suspend fun fetchComicsForHero(map: Map<String, Any>)
}

package com.architectcoders.data.source

import com.architectcoders.domain.character.Character

interface LocalDataSource {
    suspend fun getLocalCharacterById(characterId: Int): Character
    suspend fun getLastTimeStampFromCharacterEntity(): Long?
    suspend fun getLocalCharactersCount(): Int
    fun isLocalCharacterFavorite(characterId: Int): Any
    suspend fun insertAllLocalCharacters(characterList: List<Character>)
    suspend fun deleteAllLocalCharacters()
    suspend fun getLocalFavoriteCharacters(): Any
    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character)
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character)
    fun getPagingSourceFromCharacterEntity(): Any?
    fun getComicsForCharacter(characterId: Int): Any
    suspend fun insertRemoteComicsForLocalCharacter(map: Map<String, Any>)
    suspend fun isPasswordAlreadyStored(): Boolean
    suspend fun saveCredentials(password: String, recoveryHint: String)
    suspend fun deleteCredentials()
    suspend fun isPasswordCorrect(password: String): Boolean
    suspend fun isRecoveryHintCorrect(recoveryHint: String): Boolean
    suspend fun deleteAllLocalFavoriteCharacter()
}

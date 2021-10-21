package com.architectcoders.data.repository

import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.ComicsPayload
import javax.inject.Inject

class MarvelRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getRemoteCharacters(offset: Int): CharactersPayload =
        remoteDataSource.getRemoteCharacters(offset)

    suspend fun getRemoteComicsFromCharacterId(characterId: Int): ComicsPayload? =
        remoteDataSource.getRemoteComics(characterId)

    suspend fun getLocalCharactersCount(): Int =
        localDataSource.getLocalCharactersCount()

    suspend fun getLocalCharacterById(characterId: Int): Character =
        localDataSource.getLocalCharacterById(characterId)

    fun isLocalCharacterFavorite(characterId: Int): Any =
        localDataSource.isLocalCharacterFavorite(characterId)

    suspend fun insertAllLocalCharacters(characterList: List<Character>) =
        localDataSource.insertAllLocalCharacters(characterList)

    suspend fun getLocalFavoriteCharacters(): Any =
        localDataSource.getLocalFavoriteCharacters()

    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character) =
        localDataSource.insertLocalFavoriteCharacter(favoriteCharacter)

    suspend fun deleteAllLocalCharacters() =
        localDataSource.deleteAllLocalCharacters()

    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character) =
        localDataSource.deleteLocalFavoriteCharacter(favoriteCharacter)

    suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        localDataSource.getLastTimeStampFromCharacterEntity()

    fun getPagingSourceFromCharacterEntity(): Any? =
        localDataSource.getPagingSourceFromCharacterEntity()

    suspend fun insertRemoteComicsForLocalCharacter(map: Map<String, Any>) =
        localDataSource.insertRemoteComicsForLocalCharacter(map)

    fun getComicsForCharacter(characterId: Int): Any =
        localDataSource.getComicsForCharacter(characterId)

    suspend fun isPasswordAlreadyStored(): Boolean =
        localDataSource.isPasswordAlreadyStored()

    suspend fun saveCredentials(password: String, recoveryHint: String): Unit =
        localDataSource.saveCredentials(password, recoveryHint)

    suspend fun deleteCredentials(): Unit =
        localDataSource.deleteCredentials()

    suspend fun isPasswordCorrect(password: String): Boolean =
        localDataSource.isPasswordCorrect(password)

    suspend fun isRecoveryHintCorrect(recoveryHint: String): Boolean =
        localDataSource.isRecoveryHintCorrect(recoveryHint)

    suspend fun deleteAllLocalFavoriteCharacter() =
        localDataSource.deleteAllLocalFavoriteCharacter()
}

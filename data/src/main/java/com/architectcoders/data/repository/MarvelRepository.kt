package com.architectcoders.data.repository

import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.ComicsPayload

class MarvelRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    suspend fun getRemoteCharacters(offset: Int): CharactersPayload =
        remoteDataSource.getRemoteCharacters(offset)

    suspend fun getLocalCharactersCount(): Int =
        localDataSource.getLocalCharactersCount()

    suspend fun getLocalCharacterById(characterId: Int): Character =
        localDataSource.getLocalCharacterById(characterId)

    fun isLocalCharacterFavorite(characterId: Int): Any =
        localDataSource.isLocalCharacterFavorite(characterId)

    suspend fun getRemoteComicsFromCharacterId(characterId: Int): ComicsPayload? =
        remoteDataSource.getRemoteComics(characterId)

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

    suspend fun fetchComicsForCharacter(map: Map<String, Any>) =
        localDataSource.fetchComicsForCharacter(map)

    fun getComicsForCharacter(characterId: Int): Any =
        localDataSource.getComicsForCharacter(characterId)
}

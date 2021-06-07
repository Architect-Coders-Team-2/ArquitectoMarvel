package com.architectcoders.data.repository

import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.ComicsPayload
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic

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

    suspend fun isLocalCharacterFavorite(characterId: Int): Boolean =
        localDataSource.isLocalCharacterFavorite(characterId)

    suspend fun getRemoteComicsFromCharacterId(characterId: Int): ComicsPayload? =
        remoteDataSource.getRemoteComics(characterId)

    suspend fun insertAllLocalCharacters(characterList: List<Character>) =
        localDataSource.insertAllLocalCharacters(characterList)

    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character) =
        localDataSource.insertLocalFavoriteCharacter(favoriteCharacter)

    suspend fun insertLocalFavoriteComic(favoriteComic: Comic) =
        localDataSource.insertLocalFavoriteComic(favoriteComic)

    suspend fun deleteAllLocalCharacters() =
        localDataSource.deleteAllLocalCharacters()

    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character) =
        localDataSource.deleteLocalFavoriteCharacter(favoriteCharacter)

    suspend fun deleteLocalFavoriteComic(favoriteComic: Comic) =
        localDataSource.deleteLocalFavoriteComic(favoriteComic)

    suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        localDataSource.getLastTimeStampFromCharacterEntity()

    fun getPagingSourceFromCharacterEntity(): Any? =
        localDataSource.getPagingSourceFromCharacterEntity()
}

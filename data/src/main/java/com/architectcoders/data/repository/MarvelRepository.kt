package com.architectcoders.data.repository

import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.characters.Characters
import com.architectcoders.domain.comics.Comic
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class MarvelRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {
    suspend fun getRemoteCharacters(offset: Int): Characters =
        remoteDataSource.getRemoteCharacters(offset)

    suspend fun getLocalCharactersCount(): Int =
        localDataSource.getLocalCharactersCount()

    suspend fun getLocalCharacterById(characterId: Int): CharacterResult =
        localDataSource.getLocalCharacterById(characterId)

    suspend fun isLocalCharacterFavorite(characterId: Int): Boolean =
        localDataSource.isLocalCharacterFavorite(characterId)

    suspend fun getRemoteComicsFromCharacterId(characterId: Int): Comic? =
        remoteDataSource.getRemoteComics(characterId)

    suspend fun insertAllLocalCharacters(characterList: List<CharacterResult>) =
        localDataSource.insertAllLocalCharacters(characterList)

    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        localDataSource.insertLocalFavoriteCharacter(favoriteCharacter)

    suspend fun insertLocalFavoriteComic(favoriteComic: ComicResult) =
        localDataSource.insertLocalFavoriteComic(favoriteComic)

    suspend fun deleteAllLocalCharacters() =
        localDataSource.deleteAllLocalCharacters()

    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        localDataSource.deleteLocalFavoriteCharacter(favoriteCharacter)

    suspend fun deleteLocalFavoriteComic(favoriteComic: ComicResult) =
        localDataSource.deleteLocalFavoriteComic(favoriteComic)

    suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        localDataSource.getLastTimeStampFromCharacterEntity()

    fun getPagingSourceFromCharacterEntity(): Any? =
        localDataSource.getPagingSourceFromCharacterEntity()
}

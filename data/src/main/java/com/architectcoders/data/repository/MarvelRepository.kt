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
    suspend fun getLocalCharacters(): List<CharacterResult>? {
        return localDataSource.getLocalCharacters()
    }

    suspend fun getRemoteCharacters(offset: Int): Characters {
        return remoteDataSource.getRemoteCharacters(offset)
    }

    suspend fun getRemoteCharacterById(characterId: Int): Characters =
        remoteDataSource.getRemoteCharacterById(characterId)

    suspend fun isLocalCharacterFavorite(characterId: Int): Boolean =
        localDataSource.isCharacterFavorite(characterId)

    suspend fun getRemoteComicsFromCharacterId(characterId: Int): Comic? =
        remoteDataSource.getRemoteComics(characterId)

    suspend fun insertLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        localDataSource.insertFavoriteCharacter(favoriteCharacter)

    suspend fun insertLocalFavoriteComic(favoriteComic: ComicResult) =
        localDataSource.insertFavoriteDetailedComic(favoriteComic)

    suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        localDataSource.deleteFavoriteCharacter(favoriteCharacter)

    suspend fun deleteLocalFavoriteComic(favoriteComic: ComicResult) =
        localDataSource.deleteFavoriteDetailedComic(favoriteComic)
}

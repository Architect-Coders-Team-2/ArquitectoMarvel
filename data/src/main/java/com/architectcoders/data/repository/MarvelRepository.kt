package com.architectcoders.data.repository

import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource

class MarvelRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {
    suspend fun getLocalCharacters(): Any? {
        return localDataSource.getLocalCharacters()
    }

    suspend fun getRemoteCharacters(vararg param: Any): Any {
        return remoteDataSource.getRemoteCharacters(*param)
    }

    suspend fun getCharacterById(vararg param: Any): Any =
        remoteDataSource.getRemoteCharacterById(*param)

    suspend fun isCharacterFavorite(vararg param: Any): Any =
        localDataSource.isCharacterFavorite(*param)

    suspend fun getComicsFromCharacterId(vararg param: Any): Any? =
        remoteDataSource.getRemoteComics(*param)

    suspend fun insertFavoriteCharacter(vararg param: Any) =
        localDataSource.insertFavoriteCharacter(*param)

    suspend fun insertFavoriteComic(vararg param: Any) =
        localDataSource.insertFavoriteDetailedComic(*param)

    suspend fun deleteFavoriteCharacter(vararg param: Any) =
        localDataSource.deleteFavoriteCharacter(*param)

    suspend fun deleteFavoriteComic(vararg param: Any) =
        localDataSource.deleteFavoriteDetailedComic(*param)
}

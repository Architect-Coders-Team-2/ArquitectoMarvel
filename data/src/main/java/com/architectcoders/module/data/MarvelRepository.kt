package com.architectcoders.module.data

import com.architectcoders.module.data.sources.LocalDataSource
import com.architectcoders.module.data.sources.RemoteDataSource
import com.architectcoders.module.domain.local_models.DetailedComic
import com.architectcoders.module.domain.remote_models.Characters.Characters
import com.architectcoders.module.domain.remote_models.Comics.Comics
import com.architectcoders.module.domain.remote_models.Characters.Result as CharacterResult

class MarvelRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCharactersRemote(
        offset: Int
    ): Characters {
        return remoteDataSource.getCharacters(offset)
    }

    suspend fun getComicsFromCharacterRemote(
        characterId: Int
    ): Comics {
        return remoteDataSource.getComics(characterId)
    }

    suspend fun insertFavoriteCharacter(favouriteCharacter: CharacterResult) {
        localDataSource.insertFavoriteCharacter(favouriteCharacter)
    }

    suspend fun insertFavoriteComic(comic: DetailedComic) {
        localDataSource.insertFavoriteDetailedComic(comic)
    }

    suspend fun deleteFavoriteCharacters(favouriteCharacter: CharacterResult) {
        localDataSource.deleteFavoriteCharacter(favouriteCharacter)
    }

    suspend fun deleteFavoriteDetailedComic(comic: DetailedComic) {
        localDataSource.deleteFavoriteDetailedComic(comic)
    }

    suspend fun isCharacterFavorite(characterId: Int): Boolean  {
        return localDataSource.isCharacterFavorite(characterId)
    }

}
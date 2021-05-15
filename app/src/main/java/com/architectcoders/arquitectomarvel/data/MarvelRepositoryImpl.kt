package com.architectcoders.arquitectomarvel.data

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.data.sources.LocalDataSource
import com.architectcoders.module.data.sources.RemoteDataSource
import com.architectcoders.module.domain.models.Characters.Characters
import com.architectcoders.module.domain.models.Characters.ResultCharacters
import com.architectcoders.module.domain.models.Comics.Comics
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): MarvelRepository {

    override suspend fun getCharactersRemote(
        offset: Int
    ): Characters {
        return remoteDataSource.getCharacters(offset)
    }

    override suspend fun getComicsFromCharacterRemote(
        characterId: Int
    ): Comics {
        return remoteDataSource.getComics(characterId)
    }

    override suspend fun insertFavoriteCharacter(favouriteCharacter: ResultCharacters) {
        localDataSource.insertFavoriteCharacter(favouriteCharacter)
    }

    override suspend fun deleteFavoriteCharacters(favouriteCharacter: ResultCharacters) {
        localDataSource.deleteFavoriteCharacter(favouriteCharacter)
    }

    override suspend fun isCharacterFavorite(characterId: Int): Boolean  {
        return localDataSource.isCharacterFavorite(characterId)
    }

}
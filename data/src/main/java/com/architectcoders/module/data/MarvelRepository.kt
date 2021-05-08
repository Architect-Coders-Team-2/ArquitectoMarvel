package com.architectcoders.module.data

import com.architectcoders.module.domain.local_models.DetailedComic
import com.architectcoders.module.domain.remote_models.Characters.Characters
import com.architectcoders.module.domain.remote_models.Comics.Comics
import com.architectcoders.module.domain.remote_models.Characters.Result as CharacterResult

class MarvelRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val credentialsApiRepository: CredentialsApiRepository
) {

    suspend fun getCharactersRemote(
        offset: Int
    ): Characters {
        return remoteDataSource.getCharacters(credentialsApiRepository, offset)
    }

    suspend fun getComicsFromCharacterRemote(
        characterId: Int
    ): Comics {
        return remoteDataSource.getComics(credentialsApiRepository, characterId)
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

interface LocalDataSource {
    suspend fun insertFavoriteCharacter(toCharacterEntity: CharacterResult)
    suspend fun insertFavoriteDetailedComic(comic: DetailedComic)
    suspend fun deleteFavoriteCharacter(favouriteCharacter: CharacterResult)
    suspend fun deleteFavoriteDetailedComic(comic: DetailedComic)
    suspend fun isCharacterFavorite(characterId: Int): Boolean
}

interface RemoteDataSource {
    suspend fun getCharacters(
        credentialsApiRepository: CredentialsApiRepository,
        offset: Int
    ): Characters

    suspend fun getComics(
        credentialsApiRepository: CredentialsApiRepository,
        characterId: Int
    ): Comics
}

interface CredentialsApiRepository {
    val currentTime: Long
    val publicKey: String
    val privateKey: String
    val hash: String
}
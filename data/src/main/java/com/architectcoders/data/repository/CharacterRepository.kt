package com.architectcoders.data.repository

import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.characters.Characters
import com.architectcoders.domain.comics.Comic
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class CharacterRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val publicKey: String,
) {

    suspend fun getCharactersRemote(offset: Int, ts: Long, hash: String): Characters {
        return remoteDataSource.getCharacters(ts, publicKey, hash, offset)
    }

    suspend fun getCharacterById(characterId: Int, ts: Long, hash: String): Characters =
        remoteDataSource.getCharacterById(characterId, ts, publicKey, hash)

    suspend fun isCharacterFavorite(characterId: Int): Boolean =
        localDataSource.isCharacterFavorite(characterId)

    suspend fun getComicsFromCharacterId(characterId: Int, ts: Long, hash: String): Comic? =
        remoteDataSource.getComics(characterId, ts, publicKey, hash)

    suspend fun insertFavoriteCharacter(favouriteCharacter: CharacterResult) =
        localDataSource.insertFavoriteCharacter(favouriteCharacter)

    suspend fun insertFavoriteComic(comic: ComicResult) =
        localDataSource.insertFavoriteDetailedComic(comic)

    suspend fun deleteFavoriteCharacter(favouriteCharacter: CharacterResult) =
        localDataSource.deleteFavoriteCharacter(favouriteCharacter)

    suspend fun deleteFavoriteComic(comic: ComicResult) =
        localDataSource.deleteFavoriteDetailedComic(comic)
}

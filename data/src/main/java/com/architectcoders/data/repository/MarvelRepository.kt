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
    private val publicKey: String,
) {


    //Remote
    suspend fun getCharactersRemote(offset: Int, ts: Long, hash: String): Characters =
        remoteDataSource.getCharacters(ts, publicKey, hash, offset)

    suspend fun getCharacterByIdRemote(characterId: Int, ts: Long, hash: String): Characters =
        remoteDataSource.getCharacterById(characterId, ts, publicKey, hash)

    suspend fun getComicsFromHeroIdRemote(characterId: Int, ts: Long, hash: String): Comic? =
        remoteDataSource.getComics(characterId, ts, publicKey, hash)


    // Local
    suspend fun isHeroFavorite(heroId: Int): Boolean =
        localDataSource.isHeroFavorite(heroId)

    suspend fun insertFavoriteHero(favouriteHero: CharacterResult) =
        localDataSource.insertFavoriteHero(favouriteHero)

    suspend fun insertFavoriteComic(comic: ComicResult) =
        localDataSource.insertFavoriteComic(comic)

    suspend fun deleteFavoriteHero(favouriteHero: CharacterResult) =
        localDataSource.deleteFavoriteHero(favouriteHero)

    suspend fun deleteFavoriteComic(comic: ComicResult) =
        localDataSource.deleteFavoriteComic(comic)

}

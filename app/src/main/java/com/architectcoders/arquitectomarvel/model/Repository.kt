package com.architectcoders.arquitectomarvel.model

import android.app.Activity
import android.app.Application
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.model.characters.Characters
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.comics.Comic
import com.architectcoders.arquitectomarvel.model.database.DetailedComicEntity
import com.architectcoders.arquitectomarvel.model.database.ResultDao
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase
import com.architectcoders.arquitectomarvel.model.database.toCharacterEntity

class Repository(private val application: Application) {

    suspend fun getCharactersRemote(): Characters {
        val ts = System.currentTimeMillis()
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        return MarvelApiRest.service.getCharacters(ts, publicKey, hash)
    }

    val dao: ResultDao get() = ResultDatabase.getInstance(application).resultDao

    suspend fun getComicsFromCharacterRemote(characterId: Int): Comic? {
        val ts = System.currentTimeMillis()
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        return MarvelApiRest.service.getComics(characterId, ts, publicKey, hash)
    }

    suspend fun insertFavoriteCharacter(favouriteCharacter: Result) {
        dao.insertFavoriteCharacter(favouriteCharacter.toCharacterEntity)
    }

    suspend fun insertFavoriteComic(comic: DetailedComicEntity) {
        dao.insertFavoriteDetailedComic(comic)
    }

    suspend fun deleteFavoriteCharacters(favouriteCharacter: Result) {
        dao.deleteFavoriteCharacter(favouriteCharacter.toCharacterEntity)
    }

    suspend fun deleteFavoriteDetailedComic(comic: DetailedComicEntity) {
        dao.deleteFavoriteDetailedComic(comic)
    }

    suspend fun isCharacterFavorite(characterId: Int): Boolean =
        dao.isCharacterFavorite(characterId) != null

}
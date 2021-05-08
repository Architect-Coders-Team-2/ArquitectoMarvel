package com.architectcoders.arquitectomarvel.model

import android.app.Application
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.model.characters.Characters
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.comics.Comic
import com.architectcoders.arquitectomarvel.model.database.DetailedComicEntity
import com.architectcoders.arquitectomarvel.model.database.ResultDao
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase
import com.architectcoders.arquitectomarvel.model.database.toCharacterEntity
import timber.log.Timber

class Repository(private val application: Application) {

    val dao: ResultDao get() = ResultDatabase.getInstance(application).resultDao

    suspend fun getCharactersRemote(offset: Int): Characters {
        val ts = System.currentTimeMillis()
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        val response = MarvelApiRest.service.getCharacters(ts, publicKey, hash, offset)
        Timber.d("qq_Repository.getCharactersRemote: ----- ()")
        Timber.d("qq_Repository.getCharactersRemote: ${offset} (offset)")
        Timber.d("qq_Repository.getCharactersRemote: ${response.characterData?.results?.size} (response.characterData?.results?.size)")
        return response
    }

    suspend fun getComicsFromCharacterRemote(characterId: Int): Comic {
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
        dao.isCharacterFavorite(characterId).isNotEmpty()
}

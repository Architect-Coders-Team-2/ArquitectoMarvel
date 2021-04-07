package com.architectcoders.arquitectomarvel.model

import android.app.Activity
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.model.characters.Characters
import com.architectcoders.arquitectomarvel.model.comics.Comic
import com.architectcoders.arquitectomarvel.model.database.character.ResultDao
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase

class Repository(private val activity: Activity) {

    suspend fun getCharactersRemote(): Characters {
        val ts = System.currentTimeMillis()
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        return MarvelApiRest.service.getCharacters(ts, publicKey, hash)
    }

    suspend fun getComicsFromCharacterRemote(characterId: Int): Comic? {
        val ts = System.currentTimeMillis()
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        return MarvelApiRest.service.getComics(characterId, ts, publicKey, hash)
    }

    val dao: ResultDao get() = ResultDatabase.getInstance(activity).resultDao
}

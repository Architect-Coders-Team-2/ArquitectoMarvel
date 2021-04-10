package com.architectcoders.arquitectomarvel.model

import android.app.Activity
import android.app.Application
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.model.characters.Characters
import com.architectcoders.arquitectomarvel.model.database.ResultDao
import com.architectcoders.arquitectomarvel.model.database.ResultDatabase

class Repository(private val application: Application) {

    suspend fun getCharactersRemote(): Characters {
        val ts = System.currentTimeMillis()
        val publicKey = BuildConfig.MARVEL_API_KEY
        val privateKey = BuildConfig.MARVEL_PRIVATE_KEY
        val hash = "$ts$privateKey$publicKey".md5
        return MarvelApiRest.service.getCharacters(ts, publicKey, hash)
    }

    val dao: ResultDao get() = ResultDatabase.getInstance(application).resultDao

}
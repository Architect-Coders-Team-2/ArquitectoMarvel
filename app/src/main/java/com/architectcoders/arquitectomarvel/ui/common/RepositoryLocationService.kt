package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.data.database.CharacterDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource

object RepositoryLocationService {

    fun providerRepository(context: Context): CharacterRepository {
        val marvelDataSource: RemoteDataSource =
            MarvelDataSource()
        val roomDataSource: LocalDataSource =
            RoomDataSource(CharacterDatabase.getInstance(context))
        val ts = System.currentTimeMillis()
        val apiKey = BuildConfig.MARVEL_API_KEY
        val hash = "${ts}${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_API_KEY}".md5

        return CharacterRepository(
            marvelDataSource,
            roomDataSource,
            ts,
            apiKey,
            hash
        )

    }
}
package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import com.architectcoders.arquitectomarvel.data.CredentialsSourceImpl
import com.architectcoders.arquitectomarvel.data.local.ResultDatabase
import com.architectcoders.arquitectomarvel.data.local.RoomDataSource
import com.architectcoders.arquitectomarvel.data.remote.RetrofitDataSource
import com.architectcoders.module.data.sources.CredentialsSource
import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.data.sources.LocalDataSource
import com.architectcoders.module.data.sources.RemoteDataSource

object ServiceLocator {

    fun provideMarvelRepository(context: Context): MarvelRepository {
        val roomDataSource: LocalDataSource = RoomDataSource(ResultDatabase.getInstance(context))
        val credentialsSource: CredentialsSource = CredentialsSourceImpl()
        val retrofitDataSource: RemoteDataSource = RetrofitDataSource(credentialsSource)
        return MarvelRepository(
            roomDataSource,
            retrofitDataSource
        )
    }
}
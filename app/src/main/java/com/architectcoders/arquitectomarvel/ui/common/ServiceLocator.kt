package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelCredentialDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.arquitectomarvel.internetconnection.NetworkDataSourceImpl
import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.data.repository.MarvelRepository

object ServiceLocator {

    fun provideNetworkRepository(
        context: Context
    ): NetworkRepository =
        NetworkRepository(
            NetworkDataSourceImpl(
                context
            )
        )

    fun provideMarvelRepository(context: Context): MarvelRepository =
        MarvelRepository(
            MarvelDataSource(MarvelCredentialDataSource()),
            RoomDataSource(MarvelDatabase.getInstance(context))
        )
}

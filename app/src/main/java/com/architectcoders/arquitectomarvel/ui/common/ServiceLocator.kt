package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelCredentialDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.arquitectomarvel.internetconnection.InternetConnectioChecketImpl
import com.architectcoders.data.repository.InternetAvaibleRepo
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.data.source.InternetConnectionSource

object ServiceLocator {

    fun internetConnectionProvider(
        context: Context,
        lifecycle: Lifecycle
    ): InternetConnectionSource =
        InternetConnectioChecketImpl(
            context,
            lifecycle
        )

    fun provideInternetProvideRepo(
        context: Context,
        lifecycle: Lifecycle
    ) = InternetAvaibleRepo(
        internetConnectionProvider(
            context,
            lifecycle
        )
    )

    fun provideMarvelRepository(context: Context): MarvelRepository =
        MarvelRepository(
            MarvelDataSource(MarvelCredentialDataSource()),
            RoomDataSource(MarvelDatabase.getInstance(context))
        )
}

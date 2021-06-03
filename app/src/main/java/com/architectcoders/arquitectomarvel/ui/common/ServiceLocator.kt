package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
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
        lifecycle: Lifecycle,
        lifecycleCoroutineScope: LifecycleCoroutineScope
    ): InternetConnectionSource =
        InternetConnectioChecketImpl(
            context,
            lifecycle,
            lifecycleCoroutineScope
        )

    fun provideInternetProvideRepo(
        context: Context,
        lifecycle: Lifecycle,
        lifecycleCoroutineScope: LifecycleCoroutineScope
    ) = InternetAvaibleRepo(
        internetConnectionProvider(
            context,
            lifecycle,
            lifecycleCoroutineScope
        )
    )


    fun provideMarvelRepository(context: Context): MarvelRepository =
        MarvelRepository(
            MarvelDataSource(MarvelCredentialDataSource()),
            RoomDataSource(MarvelDatabase.getInstance(context))
        )
}

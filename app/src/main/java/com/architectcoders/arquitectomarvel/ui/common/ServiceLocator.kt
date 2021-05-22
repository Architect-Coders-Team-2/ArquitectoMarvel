package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelCredentialDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.data.repository.MarvelRepository

object ServiceLocator {

    fun provideMarvelRepository(context: Context): MarvelRepository =
        MarvelRepository(
            MarvelDataSource(MarvelCredentialDataSource()),
            RoomDataSource(MarvelDatabase.getInstance(context))
        )
}

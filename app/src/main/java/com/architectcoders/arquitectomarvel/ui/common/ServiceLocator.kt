package com.architectcoders.arquitectomarvel.ui.common

import android.content.Context
import com.architectcoders.arquitectomarvel.biometric.BiometricDataSourceImpl
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelCredentialDataSource
import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.arquitectomarvel.data.server.RetrofitDataSource
import com.architectcoders.arquitectomarvel.network.NetworkDataSourceImpl
import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.data.repository.MarvelRepository

object ServiceLocator {

    fun provideBiometricRepository(
        context: Context
    ): BiometricRepository =
        BiometricRepository(
            BiometricDataSourceImpl(
                context
            )
        )

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
            RetrofitDataSource(MarvelCredentialDataSource()),
            RoomDataSource(MarvelDatabase.getInstance(context))
        )
}

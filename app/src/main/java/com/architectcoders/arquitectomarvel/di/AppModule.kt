package com.architectcoders.arquitectomarvel.di

import android.content.Context
import androidx.room.Room
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelCredentialDataSource
import com.architectcoders.arquitectomarvel.data.server.RetrofitDataSource
import com.architectcoders.arquitectomarvel.network.NetworkDataSourceImpl
import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.NetworkDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModuleProvider {

    @Singleton
    @Provides
    fun databaseProvider(@ApplicationContext appContext: Context): MarvelDatabase =
        Room.databaseBuilder(
            appContext,
            MarvelDatabase::class.java,
            "marvelDb"
        ).build()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinder {

    @Singleton
    @Binds
    abstract fun bindsRoomDataSource(roomDataSource: RoomDataSource): LocalDataSource

    @Singleton
    @Binds
    abstract fun bindsCredentialDataSource(marvelCredentialDataSource: MarvelCredentialDataSource): CredentialsDataSource

    @Singleton
    @Binds
    abstract fun bindsRetrofitDataSource(retrofitDataSource: RetrofitDataSource): RemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsNetworkDataSourceProvider(networkDataSourceImpl: NetworkDataSourceImpl): NetworkDataSource
}

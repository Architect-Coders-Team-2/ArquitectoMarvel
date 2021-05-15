package com.architectcoders.arquitectomarvel.di

import android.app.Application
import androidx.room.Room
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.data.database.CharacterDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = MarvelDataSource()

    @Provides
    @Singleton
    fun databaseProvider(app: Application): CharacterDatabase = Room.databaseBuilder(
        app,
        CharacterDatabase::class.java,
        "result_db"
    ).build()

    @Provides
    fun localDataSourceProvider(characterDatabase: CharacterDatabase): LocalDataSource =
        RoomDataSource(characterDatabase)

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = BuildConfig.MARVEL_API_KEY
}

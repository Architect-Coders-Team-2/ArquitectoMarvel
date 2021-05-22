package com.architectcoders.arquitectomarvel.di

import android.app.Application
import androidx.room.Room
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.data.database.CharacterDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.arquitectomarvel.ui.common.md5
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("ts")
    fun tsProvider(): Long = System.currentTimeMillis()

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = BuildConfig.MARVEL_API_KEY

    @Provides
    @Singleton
    @Named("hash")
    fun hashProvider(): String = "${tsProvider()}${BuildConfig.MARVEL_PRIVATE_KEY}${apiKeyProvider()}".md5

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        CharacterDatabase::class.java,
        "result_db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: CharacterDatabase): LocalDataSource =
        RoomDataSource(db)

    @Provides
    fun remoteDataSoruceProvider(): RemoteDataSource =
        MarvelDataSource()


}
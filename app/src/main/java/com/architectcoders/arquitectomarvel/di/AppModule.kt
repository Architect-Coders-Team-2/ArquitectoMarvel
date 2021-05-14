package com.architectcoders.arquitectomarvel.di

import android.app.Application
import androidx.room.Room
import com.architectcoders.arquitectomarvel.data.CredentialsSourceImpl
import com.architectcoders.arquitectomarvel.data.local.ResultDatabase
import com.architectcoders.arquitectomarvel.data.local.RoomDataSource
import com.architectcoders.arquitectomarvel.data.remote.RetrofitDataSource
import com.architectcoders.module.data.sources.CredentialsSource
import com.architectcoders.module.data.sources.LocalDataSource
import com.architectcoders.module.data.sources.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) : ResultDatabase =
        Room.databaseBuilder(
            app,
            ResultDatabase::class.java,
            "result_db"
        ).build()

    @Provides
    @Singleton
    fun localDataSourceProvider(db: ResultDatabase): LocalDataSource =
        RoomDataSource(db)

    @Provides
    @Singleton
    fun credentialsProvider(): CredentialsSource =
        CredentialsSourceImpl()

    @Provides
    @Singleton
    fun remoteDataSourceProvider(credentialsSource: CredentialsSource): RemoteDataSource =
        RetrofitDataSource(credentialsSource)
}
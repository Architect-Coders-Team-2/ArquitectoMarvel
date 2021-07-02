package com.architectcoders.arquitectomarvel.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelCredentialDataSource
import com.architectcoders.arquitectomarvel.data.server.RetrofitDataSource
import com.architectcoders.arquitectomarvel.network.NetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun databaseProvider(@ApplicationContext appContext: Context): MarvelDatabase =
        Room.databaseBuilder(
            appContext,
            MarvelDatabase::class.java,
            "marvelDb"
        ).build()

    @Singleton
    @Provides
    fun roomDataSourceProvider(db: MarvelDatabase): RoomDataSource = RoomDataSource(db)

    @Singleton
    @Provides
    fun retrofitDataSourceProvider(): RetrofitDataSource =
        RetrofitDataSource(MarvelCredentialDataSource())

    @Singleton
    @Provides
    fun networkDataSourceProvider(app: Application): NetworkDataSourceImpl =
        NetworkDataSourceImpl(app)
}

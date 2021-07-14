package com.architectcoders.arquitectomarvel.data.database.di

import android.content.Context
import androidx.room.Room
import com.architectcoders.arquitectomarvel.data.database.MarvelDao
import com.architectcoders.arquitectomarvel.data.database.MarvelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun databaseRoomProvider(@ApplicationContext context: Context): MarvelDatabase =
        Room.databaseBuilder(
            context,
            MarvelDatabase::class.java,
            "result_db"
        ).build()


    @Singleton
    @Provides
    fun providerDao(appDatabase: MarvelDatabase): MarvelDao {
        return appDatabase.marvelDao
    }
}
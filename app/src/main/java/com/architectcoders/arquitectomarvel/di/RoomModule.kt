package com.architectcoders.arquitectomarvel.di

import android.content.Context
import androidx.room.Room
import com.architectcoders.arquitectomarvel.data.local.ResultDao
import com.architectcoders.arquitectomarvel.data.local.ResultDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun databaseProvider(@ApplicationContext context: Context): ResultDatabase =
        Room.databaseBuilder(
            context,
            ResultDatabase::class.java,
            "result_db"
        ).build()


    @Provides
    fun provideChannelDao(appDatabase: ResultDatabase): ResultDao {
        return appDatabase.resultDao()
    }
}
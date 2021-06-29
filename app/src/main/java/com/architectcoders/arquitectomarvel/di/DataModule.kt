package com.architectcoders.arquitectomarvel.di

import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.RetrofitDataSource
import com.architectcoders.arquitectomarvel.network.NetworkDataSourceImpl
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.data.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun marvelRepositoryProvider(
        retrofitDataSource: RetrofitDataSource,
        roomDataSource: RoomDataSource
    ): MarvelRepository = MarvelRepository(retrofitDataSource, roomDataSource)

    @Singleton
    @Provides
    fun networkRepositoryProvider(networkDataSourceImpl: NetworkDataSourceImpl): NetworkRepository =
        NetworkRepository(networkDataSourceImpl)
}

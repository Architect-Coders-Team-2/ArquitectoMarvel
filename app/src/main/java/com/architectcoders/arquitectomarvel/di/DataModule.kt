package com.architectcoders.arquitectomarvel.di

import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelCredentialDataSource
import com.architectcoders.arquitectomarvel.data.server.RetrofitDataSource
import com.architectcoders.arquitectomarvel.network.NetworkDataSourceImpl
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.NetworkDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModuleProvider {

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

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModuleBinder {

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
    abstract fun bindsNetworkDataSource(networkDataSourceImpl: NetworkDataSourceImpl): NetworkDataSource
}

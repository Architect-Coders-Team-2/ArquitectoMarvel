package com.architectcoders.arquitectomarvel.di

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
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModuleBinderForRoom {

    @Singleton
    @Binds
    abstract fun bindsRoomDataSource(roomDataSource: RoomDataSource): LocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModuleBinderForCredentials {

    @Singleton
    @Binds
    abstract fun bindsCredentialDataSource(
        marvelCredentialDataSource: MarvelCredentialDataSource
    ): CredentialsDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModuleBinderForRetrofit {

    @Singleton
    @Binds
    abstract fun bindsRetrofitDataSource(retrofitDataSource: RetrofitDataSource): RemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModuleBinderForNetwork {

    @Singleton
    @Binds
    abstract fun bindsNetworkDataSource(networkDataSourceImpl: NetworkDataSourceImpl): NetworkDataSource
}

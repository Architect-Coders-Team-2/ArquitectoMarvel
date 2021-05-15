package com.architectcoders.arquitectomarvel.di

import com.architectcoders.arquitectomarvel.data.CredentialsSourceImpl
import com.architectcoders.arquitectomarvel.data.MarvelRepositoryImpl
import com.architectcoders.arquitectomarvel.data.local.RoomDataSource
import com.architectcoders.arquitectomarvel.data.remote.RetrofitDataSource
import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.data.sources.CredentialsSource
import com.architectcoders.module.data.sources.LocalDataSource
import com.architectcoders.module.data.sources.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindLocalDataSource(
        roomDataSource: RoomDataSource
    ): LocalDataSource

    @Binds
    abstract fun bindCredentialSource(
        credentialsSourceImpl: CredentialsSourceImpl
    ): CredentialsSource

    @Binds
    abstract fun bindRemoteDataSource(
        retrofitDataSource: RetrofitDataSource
    ): RemoteDataSource

    @Binds
    abstract fun bindMarvelRepository(
        marvelRepositoryImpl: MarvelRepositoryImpl
    ): MarvelRepository

}


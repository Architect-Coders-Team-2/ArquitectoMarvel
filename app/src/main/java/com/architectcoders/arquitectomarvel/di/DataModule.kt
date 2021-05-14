package com.architectcoders.arquitectomarvel.di

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.data.sources.LocalDataSource
import com.architectcoders.module.data.sources.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun marvelRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MarvelRepository = MarvelRepository(localDataSource, remoteDataSource)
}
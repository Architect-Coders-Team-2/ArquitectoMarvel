package com.architectcoders.arquitectomarvel.di

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun characterRepositoryProvider(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        @Named("apiKey") publicKey: String
    ): CharacterRepository = CharacterRepository(remoteDataSource, localDataSource, publicKey)
}

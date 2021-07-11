package com.architectcoders.arquitectomarvel.di

import android.content.Context
import com.architectcoders.arquitectomarvel.network.NetworkDataSourceImpl
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.NetworkDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    // For MainViewModel
    @Singleton
    @Provides
    fun provideGetRemoteCharacters(marvelRepository: MarvelRepository): GetRemoteCharacters =
        GetRemoteCharacters(marvelRepository)

    @Singleton
    @Provides
    fun provideDeleteAllLocalCharacters(marvelRepository: MarvelRepository): DeleteAllLocalCharacters =
        DeleteAllLocalCharacters(marvelRepository)

    @Singleton
    @Provides
    fun provideInsertAllLocalCharacters(marvelRepository: MarvelRepository): InsertAllLocalCharacters =
        InsertAllLocalCharacters(marvelRepository)

    @Singleton
    @Provides
    fun provideGetLastTimeStampFrom(marvelRepository: MarvelRepository): GetLastTimeStampFromCharacterEntity =
        GetLastTimeStampFromCharacterEntity(marvelRepository)

    @Singleton
    @Provides
    fun provideGetPagingSourceFromCharacterEntity(marvelRepository: MarvelRepository): GetPagingSourceFromCharacterEntity =
        GetPagingSourceFromCharacterEntity(marvelRepository)

    @Singleton
    @Provides
    fun provideGetLocalCharactersCount(marvelRepository: MarvelRepository): GetLocalCharactersCount =
        GetLocalCharactersCount(marvelRepository)

    // MarvelRepo
    @Singleton
    @Provides
    fun provideMarvelRepo(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): MarvelRepository = MarvelRepository(
        remoteDataSource, localDataSource
    )

    // NetworkRepo
    @Singleton
    @Provides
    fun provideNetworkDataSource(
        @ApplicationContext appContext: Context
    ): NetworkDataSource = NetworkDataSourceImpl(
        appContext
    )

    @Singleton
    @Provides
    fun provideNetworkRepo(
        networkDataSource: NetworkDataSource
    ): NetworkRepository = NetworkRepository(
        networkDataSource
    )

    // For FavorityCharacterViewModel
    @Singleton
    @Provides
    fun provideGetLocalFavoriteCharacters(
        marvelRepository: MarvelRepository
    ): GetLocalFavoriteCharacters = GetLocalFavoriteCharacters(marvelRepository)
}
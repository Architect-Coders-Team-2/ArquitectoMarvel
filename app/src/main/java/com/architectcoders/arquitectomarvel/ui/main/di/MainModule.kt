package com.architectcoders.arquitectomarvel.ui.main.di


import androidx.paging.ExperimentalPagingApi
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class MainModule {

    @ViewModelScoped
    @Provides
    fun getRemoteCharactersProvider(marvelRepository: MarvelRepository): GetRemoteCharacters =
        GetRemoteCharacters(marvelRepository)

    @ViewModelScoped
    @Provides
    fun deleteAllLocalCharactersProvider(marvelRepository: MarvelRepository): DeleteAllLocalCharacters =
        DeleteAllLocalCharacters(marvelRepository)

    @ViewModelScoped
    @Provides
    fun insertAllLocalCharactersProvider(marvelRepository: MarvelRepository): InsertAllLocalCharacters =
        InsertAllLocalCharacters(marvelRepository)

    @ViewModelScoped
    @Provides
    fun getLastTimeStampFromCharacterEntityProvider(
        marvelRepository: MarvelRepository
    ): GetLastTimeStampFromCharacterEntity =
        GetLastTimeStampFromCharacterEntity(marvelRepository)

    @ViewModelScoped
    @Provides
    fun getLocalCharactersCountProvider(marvelRepository: MarvelRepository): GetLocalCharactersCount =
        GetLocalCharactersCount(marvelRepository)

    @ViewModelScoped
    @Provides
    fun getPagingSourceFromCharacterEntityProvider(
        marvelRepository: MarvelRepository
    ): GetPagingSourceFromCharacterEntity =
        GetPagingSourceFromCharacterEntity(marvelRepository)
}
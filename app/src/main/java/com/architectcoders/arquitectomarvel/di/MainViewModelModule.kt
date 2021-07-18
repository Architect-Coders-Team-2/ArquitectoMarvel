package com.architectcoders.arquitectomarvel.di

import androidx.paging.ExperimentalPagingApi
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MainViewModelModule {

    @Provides
    fun provideGetRemoteCharacters(marvelRepository: MarvelRepository): GetRemoteCharacters =
        GetRemoteCharacters(marvelRepository)

    @Provides
    fun provideDeleteAllLocalCharacters(marvelRepository: MarvelRepository): DeleteAllLocalCharacters =
        DeleteAllLocalCharacters(marvelRepository)

    @Provides
    fun provideInsertAllLocalCharacters(marvelRepository: MarvelRepository): InsertAllLocalCharacters =
        InsertAllLocalCharacters(marvelRepository)

    @Provides
    fun provideGetLastTimeStampFrom(marvelRepository: MarvelRepository): GetLastTimeStampFromCharacterEntity =
        GetLastTimeStampFromCharacterEntity(marvelRepository)

    @Provides
    fun provideGetPagingSourceFromCharacterEntity(marvelRepository: MarvelRepository): GetPagingSourceFromCharacterEntity =
        GetPagingSourceFromCharacterEntity(marvelRepository)

    @Provides
    fun provideGetLocalCharactersCount(marvelRepository: MarvelRepository): GetLocalCharactersCount =
        GetLocalCharactersCount(marvelRepository)

    @ExperimentalPagingApi
    @Provides
    fun provideCharacterRemoteMeditor(
        getRemoteCharacters: GetRemoteCharacters,
        deleteAllLocalCharacters: DeleteAllLocalCharacters,
        insertAllLocalCharacters: InsertAllLocalCharacters,
        getLastTimeStampFromCharacterEntity: GetLastTimeStampFromCharacterEntity,
        getLocalCharactersCount: GetLocalCharactersCount
    ): CharacterRemoteMediator =
        CharacterRemoteMediator(
            getRemoteCharacters,
            deleteAllLocalCharacters,
            insertAllLocalCharacters,
            getLastTimeStampFromCharacterEntity,
            getLocalCharactersCount,
        )
}
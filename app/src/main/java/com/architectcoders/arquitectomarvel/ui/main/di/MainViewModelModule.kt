package com.architectcoders.arquitectomarvel.ui.main.di

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
class MainViewModelModule {

    @Provides
    fun getRemoteCharactersProvider(marvelRepository: MarvelRepository): GetRemoteCharacters =
        GetRemoteCharacters(marvelRepository)

    @Provides
    fun deleteAllLocalCharactersProvider(marvelRepository: MarvelRepository): DeleteAllLocalCharacters =
        DeleteAllLocalCharacters(marvelRepository)

    @Provides
    fun insertAllLocalCharactersProvider(marvelRepository: MarvelRepository): InsertAllLocalCharacters =
        InsertAllLocalCharacters(marvelRepository)

    @Provides
    fun getLastTimeStampFromCharacterEntityProvider(
        marvelRepository: MarvelRepository
    ): GetLastTimeStampFromCharacterEntity =
        GetLastTimeStampFromCharacterEntity(marvelRepository)

    @Provides
    fun getLocalCharactersCountProvider(marvelRepository: MarvelRepository): GetLocalCharactersCount =
        GetLocalCharactersCount(marvelRepository)

    @ExperimentalPagingApi
    @Provides
    fun getCharacterRemoteMediatorProvider(
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
            getLocalCharactersCount
        )

    @Provides
    fun getPagingSourceFromCharacterEntityProvider(
        marvelRepository: MarvelRepository
    ): GetPagingSourceFromCharacterEntity =
        GetPagingSourceFromCharacterEntity(marvelRepository)
}

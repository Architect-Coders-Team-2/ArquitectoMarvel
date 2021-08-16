package com.architectcoders.arquitectomarvel.di

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
class MainViewModelModule {

    @ViewModelScoped
    @Provides
    fun getPagingSourceFromCharacterEntityProvider(
        marvelRepository: MarvelRepository
    ): GetPagingSourceFromCharacterEntity =
        GetPagingSourceFromCharacterEntity(marvelRepository)

    // CharacterRemoteMediator

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

    @ExperimentalPagingApi
    @ViewModelScoped
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

    // Password management

    @ViewModelScoped
    @Provides
    fun isPasswordAlreadyStoredProvider(marvelRepository: MarvelRepository): IsPasswordAlreadyStored =
        IsPasswordAlreadyStored(marvelRepository)

    @ViewModelScoped
    @Provides
    fun saveCredentialsProvider(marvelRepository: MarvelRepository): SaveCredentials =
        SaveCredentials(marvelRepository)

    @ViewModelScoped
    @Provides
    fun deleteCredentialsProvider(marvelRepository: MarvelRepository): DeleteCredentials =
        DeleteCredentials(marvelRepository)

    @ViewModelScoped
    @Provides
    fun isPasswordCorrect(marvelRepository: MarvelRepository): IsPasswordCorrect =
        IsPasswordCorrect(marvelRepository)

    @ViewModelScoped
    @Provides
    fun isHintCorrectProvider(marvelRepository: MarvelRepository): IsRecoveryHintCorrect =
        IsRecoveryHintCorrect(marvelRepository)

    // Favorites

    @ViewModelScoped
    @Provides
    fun deleteAllLocalFavoriteCharacterProvider(marvelRepository: MarvelRepository):
            DeleteAllLocalFavoriteCharacter = DeleteAllLocalFavoriteCharacter(marvelRepository)
}

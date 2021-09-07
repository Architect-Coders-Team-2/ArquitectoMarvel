package com.architectcoders.arquitectomarvel.fakeModules

import androidx.paging.ExperimentalPagingApi
import com.architectcoders.arquitectomarvel.di.MainViewModelModule
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MainViewModelModule::class]
)
class FakeMainViewModelModule {

    @Singleton
    @Provides
    fun getPagingSourceFromCharacterEntityProvider(
        marvelRepository: MarvelRepository
    ): GetPagingSourceFromCharacterEntity =
        GetPagingSourceFromCharacterEntity(marvelRepository)

    // CharacterRemoteMediator

    @Singleton
    @Provides
    fun getRemoteCharactersProvider(marvelRepository: MarvelRepository): GetRemoteCharacters =
        GetRemoteCharacters(marvelRepository)

    @Singleton
    @Provides
    fun deleteAllLocalCharactersProvider(marvelRepository: MarvelRepository): DeleteAllLocalCharacters =
        DeleteAllLocalCharacters(marvelRepository)

    @Singleton
    @Provides
    fun insertAllLocalCharactersProvider(marvelRepository: MarvelRepository): InsertAllLocalCharacters =
        InsertAllLocalCharacters(marvelRepository)

    @Singleton
    @Provides
    fun getLastTimeStampFromCharacterEntityProvider(
        marvelRepository: MarvelRepository
    ): GetLastTimeStampFromCharacterEntity =
        GetLastTimeStampFromCharacterEntity(marvelRepository)

    @Singleton
    @Provides
    fun getLocalCharactersCountProvider(marvelRepository: MarvelRepository): GetLocalCharactersCount =
        GetLocalCharactersCount(marvelRepository)

    @ExperimentalPagingApi
    @Singleton
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

    @Singleton
    @Provides
    fun isPasswordAlreadyStoredProvider(marvelRepository: MarvelRepository): IsPasswordAlreadyStored =
        IsPasswordAlreadyStored(marvelRepository)

    @Singleton
    @Provides
    fun saveCredentialsProvider(marvelRepository: MarvelRepository): SaveCredentials =
        SaveCredentials(marvelRepository)

    @Singleton
    @Provides
    fun deleteCredentialsProvider(marvelRepository: MarvelRepository): DeleteCredentials =
        DeleteCredentials(marvelRepository)

    @Singleton
    @Provides
    fun isPasswordCorrect(marvelRepository: MarvelRepository): IsPasswordCorrect =
        IsPasswordCorrect(marvelRepository)

    @Singleton
    @Provides
    fun isHintCorrectProvider(marvelRepository: MarvelRepository): IsRecoveryHintCorrect =
        IsRecoveryHintCorrect(marvelRepository)

    // Favorites

    @Singleton
    @Provides
    fun deleteAllLocalFavoriteCharacterProvider(marvelRepository: MarvelRepository):
            DeleteAllLocalFavoriteCharacter = DeleteAllLocalFavoriteCharacter(marvelRepository)
}

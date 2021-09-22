package com.architectcoders.arquitectomarvel.fakeModules

import com.architectcoders.arquitectomarvel.di.CharacterDetailModule
import com.architectcoders.arquitectomarvel.di.CharacterDetailModuleForComicInteractor
import com.architectcoders.arquitectomarvel.ui.detail.GetComicsInteractor
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import mockedCharacter
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CharacterDetailModule::class]
)
class FakeCharacterDetailModule {

    @Singleton
    @Provides
    fun getLocalCharacterByIdProvider(marvelRepository: MarvelRepository): GetLocalCharacterById =
        GetLocalCharacterById(marvelRepository)

    @Singleton
    @Provides
    fun isLocalCharacterFavoriteProvider(marvelRepository: MarvelRepository): IsLocalCharacterFavorite =
        IsLocalCharacterFavorite(marvelRepository)

    @Singleton
    @Provides
    fun insertLocalFavoriteCharacterProvider(marvelRepository: MarvelRepository): InsertLocalFavoriteCharacter =
        InsertLocalFavoriteCharacter(marvelRepository)

    @Singleton
    @Provides
    fun deleteLocalFavoriteCharacterProvider(marvelRepository: MarvelRepository): DeleteLocalFavoriteCharacter =
        DeleteLocalFavoriteCharacter(marvelRepository)
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CharacterDetailModuleForComicInteractor::class]
)
class FakeCharacterDetailModuleForComicInteractor {

    @Singleton
    @Provides
    fun getRemoteComicsFromCharacterIdProvider(marvelRepository: MarvelRepository): GetRemoteComicsFromCharacterId =
        GetRemoteComicsFromCharacterId(marvelRepository)

    @Singleton
    @Provides
    fun insertComicsForCharacterLocalProvider(marvelRepository: MarvelRepository): InsertRemoteComicsForLocalCharacter =
        InsertRemoteComicsForLocalCharacter(marvelRepository)

    @Singleton
    @Provides
    fun getComicsForCharacterProvider(marvelRepository: MarvelRepository): GetComicsForCharacter =
        GetComicsForCharacter(marvelRepository)

    @Singleton
    @Provides
    fun getComicsInteractorProvider(
        getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
        insertRemoteComicsForLocalCharacter: InsertRemoteComicsForLocalCharacter,
        getComicsForCharacter: GetComicsForCharacter
    ): GetComicsInteractor =
        GetComicsInteractor(
            getRemoteComicsFromCharacterId,
            insertRemoteComicsForLocalCharacter,
            getComicsForCharacter
        )
}

@Module
@InstallIn(SingletonComponent::class)
class FakeCharacterDetailModuleForCharacterId {

    @Singleton
    @Provides
    @Named("characterId")
    fun characterIdProvider(): Int = mockedCharacter.id
}

package com.architectcoders.arquitectomarvel.di

import com.architectcoders.arquitectomarvel.data.usescases_impl.*
import com.architectcoders.module.data.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class UseCasesModule {

    @Provides
    fun provideUseCaseGetCharactersRemote(
        marvelRepository: MarvelRepository
    ): UseCaseGetCharactersRemoteImpl = UseCaseGetCharactersRemoteImpl(marvelRepository)

    @Provides
    fun provideUseCaseGetComicsRemote(
        marvelRepository: MarvelRepository
    ): UseCaseGetComicsRemoteImpl = UseCaseGetComicsRemoteImpl(marvelRepository)

    @Provides
    fun provideUseCaseInsertFavoriteCharacter(
        marvelRepository: MarvelRepository
    ): UseCaseInsertFavoriteCharacterImpl = UseCaseInsertFavoriteCharacterImpl(marvelRepository)

    @Provides
    fun provideUseCaseIsCharacterFavorite(
        marvelRepository: MarvelRepository
    ): UseCaseIsCharacterFavoriteImpl = UseCaseIsCharacterFavoriteImpl(marvelRepository)

    @Provides
    fun provideUseCaseDeleteFavoriteCharacter(
        marvelRepository: MarvelRepository
    ): UseCaseDeleteFavoriteCharacterImpl = UseCaseDeleteFavoriteCharacterImpl(marvelRepository)

}


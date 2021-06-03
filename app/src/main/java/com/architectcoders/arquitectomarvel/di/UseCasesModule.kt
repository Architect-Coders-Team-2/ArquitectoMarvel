package com.architectcoders.arquitectomarvel.di

import com.architectcoders.arquitectomarvel.data.usescases_impl.*
import com.architectcoders.module.usescases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    abstract fun bindUseCaseGetCharactersRemote(
        useCaseGetCharactersRemoteImpl: UseCaseGetCharactersRemoteImpl
    ): UseCaseGetCharactersRemote

    @Binds
    abstract fun bindUseCaseInsertFavoriteCharacter(
        useCaseInsertFavoriteCharacterImpl: UseCaseInsertFavoriteCharacterImpl
    ): UseCaseInsertFavoriteCharacter

    @Binds
    abstract fun bindUseCaseIsCharacterFavorite(
        useCaseIsCharacterFavoriteImpl: UseCaseIsCharacterFavoriteImpl
    ): UseCaseIsCharacterFavorite

    @Binds
    abstract fun bindUseCaseDeleteFavoriteCharacter(
        useCaseDeleteFavoriteCharacterImpl: UseCaseDeleteFavoriteCharacterImpl
    ): UseCaseDeleteFavoriteCharacter

    @Binds
    abstract fun bindUseCaseGetComicsRemote(
        useCaseGetComicsRemoteImpl: UseCaseGetComicsRemoteImpl
    ): UseCaseGetComicsRemote

}


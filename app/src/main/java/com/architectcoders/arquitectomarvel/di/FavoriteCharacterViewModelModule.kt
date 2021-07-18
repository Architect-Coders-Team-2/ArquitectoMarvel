package com.architectcoders.arquitectomarvel.di

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

object FavoriteCharacterViewModelModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    object FavoriteViewModelModule {

        @Provides
        fun provideGetLocalFavoriteCharacters(
            marvelRepository: MarvelRepository
        ): GetLocalFavoriteCharacters = GetLocalFavoriteCharacters(marvelRepository)
    }
}
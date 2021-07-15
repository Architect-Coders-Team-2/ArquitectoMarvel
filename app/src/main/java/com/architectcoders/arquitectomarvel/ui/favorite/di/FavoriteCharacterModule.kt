package com.architectcoders.arquitectomarvel.ui.favorite.di

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class FavoriteCharacterModule {

    @ViewModelScoped
    @Provides
    fun getLocalFavoriteCharactersProvider(marvelRepository: MarvelRepository): GetLocalFavoriteCharacters =
        GetLocalFavoriteCharacters(marvelRepository)
}
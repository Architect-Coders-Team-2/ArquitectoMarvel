package com.architectcoders.arquitectomarvel.ui.favorite

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class FavoriteCharacterModule {

    @Provides
    fun getLocalFavoriteCharactersProvider(marvelRepository: MarvelRepository): GetLocalFavoriteCharacters =
        GetLocalFavoriteCharacters(marvelRepository)
}

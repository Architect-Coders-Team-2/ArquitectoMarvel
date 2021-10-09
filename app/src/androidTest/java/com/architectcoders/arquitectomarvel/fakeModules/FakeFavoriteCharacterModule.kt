package com.architectcoders.arquitectomarvel.fakeModules

import com.architectcoders.arquitectomarvel.di.FavoriteCharacterModule
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FavoriteCharacterModule::class]
)
class FakeFavoriteCharacterModule {

    @Singleton
    @Provides
    fun getLocalFavoriteCharactersProvider(marvelRepository: MarvelRepository): GetLocalFavoriteCharacters =
        GetLocalFavoriteCharacters(marvelRepository)
}

package com.architectcoders.arquitectomarvel.ui.detail.di

import android.app.Activity
import com.architectcoders.arquitectomarvel.ui.common.EXTRA_SELECTED_HERO
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class CharacterDetailActivityModule{

    @Provides
    fun getCharacterByIdProvider(repository: CharacterRepository): GetCharacterById =
        GetCharacterById(repository)

    @Provides
    fun isCharacterFavoriteProvider(repository: CharacterRepository): IsCharacterFavorite =
        IsCharacterFavorite(repository)

    @Provides
    fun getComicsFromCharacterIdProvider(repository: CharacterRepository): GetComicsFromCharacterId =
        GetComicsFromCharacterId(repository)

    @Provides
    fun insertFavoriteCharacterProvider(repository: CharacterRepository): InsertFavoriteCharacter =
        InsertFavoriteCharacter(repository)

    @Provides
    fun insertFavoriteComicProvider(repository: CharacterRepository): InsertFavoriteComic =
        InsertFavoriteComic(repository)

    @Provides
    fun deleteFavoriteCharacterProvider(repository: CharacterRepository): DeleteFavoriteCharacter =
        DeleteFavoriteCharacter(repository)

    @Provides
    fun deleteFavoriteComicProvider(repository: CharacterRepository): DeleteFavoriteComic =
        DeleteFavoriteComic(repository)


}
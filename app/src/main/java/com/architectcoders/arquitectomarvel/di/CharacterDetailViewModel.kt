package com.architectcoders.arquitectomarvel.di

import androidx.lifecycle.SavedStateHandle
import com.architectcoders.arquitectomarvel.ui.common.EXTRA_SELECTED_CHARACTER
import com.architectcoders.arquitectomarvel.ui.detail.GetComicsInteractor
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object CharacterDetailViewModel {

    // For CharacterDetailViewModel
    @Provides
    @Named("characterId")
    fun characterIdProvider(stateHandle: SavedStateHandle): Int =
        stateHandle[EXTRA_SELECTED_CHARACTER]
            ?: throw IllegalStateException("Character ID not found in the State Handle")

    @Provides
    fun provideGetLocalCharacterById(marvelRepository: MarvelRepository): GetLocalCharacterById =
        GetLocalCharacterById(marvelRepository)

    @Provides
    fun provideIsLocalCharacterFavorite(marvelRepository: MarvelRepository): IsLocalCharacterFavorite =
        IsLocalCharacterFavorite(marvelRepository)

    @Provides
    fun provideInsertLocalFavoriteCharacter(marvelRepository: MarvelRepository): InsertLocalFavoriteCharacter =
        InsertLocalFavoriteCharacter(marvelRepository)

    @Provides
    fun provideDeleteLocalFavoriteCharacter(marvelRepository: MarvelRepository): DeleteLocalFavoriteCharacter =
        DeleteLocalFavoriteCharacter(marvelRepository)


    // GetComicsInteractor
    @Provides
    fun provideGetRemoteComicsFromCharacterId(marvelRepository: MarvelRepository): GetRemoteComicsFromCharacterId =
        GetRemoteComicsFromCharacterId(marvelRepository)

    @Provides
    fun provideInsertComicsForCharacterLocal(marvelRepository: MarvelRepository): InsertComicsForCharacterLocal =
        InsertComicsForCharacterLocal(marvelRepository)

    @Provides
    fun provideGetComicsForCharacter(marvelRepository: MarvelRepository): GetComicsForCharacter =
        GetComicsForCharacter(marvelRepository)

    @Provides
    fun provideGetComicsInteractor(
        getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
        insertComicsForCharacterLocal: InsertComicsForCharacterLocal,
        getComicsForCharacter: GetComicsForCharacter
    ): GetComicsInteractor =
        GetComicsInteractor(
            getRemoteComicsFromCharacterId, insertComicsForCharacterLocal, getComicsForCharacter
        )

}


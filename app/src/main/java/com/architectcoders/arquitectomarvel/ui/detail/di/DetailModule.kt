package com.architectcoders.arquitectomarvel.ui.detail.di


import androidx.lifecycle.SavedStateHandle
import com.architectcoders.arquitectomarvel.ui.common.EXTRA_SELECTED_CHARACTER
import com.architectcoders.arquitectomarvel.ui.detail.GetComicsInteractor
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DetailModule {
    @ViewModelScoped
    @Provides
    @Named("characterId")
    fun characterIdProvider(stateHandle: SavedStateHandle): Int =
        stateHandle[EXTRA_SELECTED_CHARACTER]
            ?: throw IllegalStateException("Character ID not found in the State Handle")

    @ViewModelScoped
    @Provides
    fun getLocalCharacterByIdProvider(marvelRepository: MarvelRepository): GetLocalCharacterById =
        GetLocalCharacterById(marvelRepository)

    @ViewModelScoped
    @Provides
    fun isLocalCharacterFavoriteProvider(marvelRepository: MarvelRepository): IsLocalCharacterFavorite =
        IsLocalCharacterFavorite(marvelRepository)

    @ViewModelScoped
    @Provides
    fun insertLocalFavoriteCharacterProvider(marvelRepository: MarvelRepository): InsertLocalFavoriteCharacter =
        InsertLocalFavoriteCharacter(marvelRepository)

    @ViewModelScoped
    @Provides
    fun deleteLocalFavoriteCharacterProvider(marvelRepository: MarvelRepository): DeleteLocalFavoriteCharacter =
        DeleteLocalFavoriteCharacter(marvelRepository)

    @ViewModelScoped
    @Provides
    fun getRemoteComicsFromCharacterIdProvider(marvelRepository: MarvelRepository): GetRemoteComicsFromCharacterId =
        GetRemoteComicsFromCharacterId(marvelRepository)

    @ViewModelScoped
    @Provides
    fun insertComicsForCharacterLocalProvider(marvelRepository: MarvelRepository): InsertComicsForCharacterLocal =
        InsertComicsForCharacterLocal(marvelRepository)

    @ViewModelScoped
    @Provides
    fun getComicsForCharacterProvider(marvelRepository: MarvelRepository): GetComicsForCharacter =
        GetComicsForCharacter(marvelRepository)

    @ViewModelScoped
    @Provides
    fun getComicsInteractorProvider(
        getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
        insertComicsForCharacterLocal: InsertComicsForCharacterLocal,
        getComicsForCharacter: GetComicsForCharacter
    ): GetComicsInteractor =
        GetComicsInteractor(
            getRemoteComicsFromCharacterId,
            insertComicsForCharacterLocal,
            getComicsForCharacter
        )
}
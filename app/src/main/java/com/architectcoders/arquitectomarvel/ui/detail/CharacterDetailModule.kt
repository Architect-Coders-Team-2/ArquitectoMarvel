package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.architectcoders.arquitectomarvel.ui.common.EXTRA_SELECTED_CHARACTER
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class CharacterDetailModule {

    @Provides
    @Named("characterId")
    fun characterIdProvider(stateHandle: SavedStateHandle): Int =
        stateHandle[EXTRA_SELECTED_CHARACTER]
            ?: throw IllegalStateException("Character ID not found in the State Handle")

    @Provides
    fun getLocalCharacterByIdProvider(marvelRepository: MarvelRepository): GetLocalCharacterById =
        GetLocalCharacterById(marvelRepository)

    @Provides
    fun isLocalCharacterFavoriteProvider(marvelRepository: MarvelRepository): IsLocalCharacterFavorite =
        IsLocalCharacterFavorite(marvelRepository)

    @Provides
    fun insertLocalFavoriteCharacterProvider(marvelRepository: MarvelRepository): InsertLocalFavoriteCharacter =
        InsertLocalFavoriteCharacter(marvelRepository)

    @Provides
    fun deleteLocalFavoriteCharacterProvider(marvelRepository: MarvelRepository): DeleteLocalFavoriteCharacter =
        DeleteLocalFavoriteCharacter(marvelRepository)

    @Provides
    fun getRemoteComicsFromCharacterIdProvider(marvelRepository: MarvelRepository): GetRemoteComicsFromCharacterId =
        GetRemoteComicsFromCharacterId(marvelRepository)

    @Provides
    fun insertComicsForCharacterLocalProvider(marvelRepository: MarvelRepository): InsertComicsForCharacterLocal =
        InsertComicsForCharacterLocal(marvelRepository)

    @Provides
    fun getComicsForCharacterProvider(marvelRepository: MarvelRepository): GetComicsForCharacter =
        GetComicsForCharacter(marvelRepository)

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

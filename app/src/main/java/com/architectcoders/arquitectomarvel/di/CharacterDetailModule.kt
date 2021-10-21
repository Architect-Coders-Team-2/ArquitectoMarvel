package com.architectcoders.arquitectomarvel.di

import androidx.lifecycle.SavedStateHandle
import com.architectcoders.arquitectomarvel.ui.common.EXTRA_SELECTED_CHARACTER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class CharacterDetailModule {

    @ViewModelScoped
    @Provides
    @Named("characterId")
    fun characterIdProvider(stateHandle: SavedStateHandle): Int =
        stateHandle[EXTRA_SELECTED_CHARACTER]
            ?: throw IllegalStateException("Character ID not found in the State Handle")
}

package com.architectcoders.arquitectomarvel.ui.main.di

import android.app.Activity
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.GetCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    fun getCharacterRepositoryProvider(characterRepository: CharacterRepository): GetCharacters =
       GetCharacters(characterRepository)
}

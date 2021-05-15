package com.architectcoders.arquitectomarvel.ui.main.mainDi

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.GetCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    fun getCharacterProvider(characterRepository: CharacterRepository): GetCharacters =
        GetCharacters(characterRepository)
}

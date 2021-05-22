package com.architectcoders.arquitectomarvel.ui.main.di

import com.architectcoders.arquitectomarvel.ui.main.MainViewModel
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.GetCharacters
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun mainViewModelProvider(getCharacters: GetCharacters) =
        MainViewModel(getCharacters)

    @Provides
    fun getCharacterRepositoryProvider(characterRepository: CharacterRepository): GetCharacters =
       GetCharacters(characterRepository)
}

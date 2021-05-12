package com.architectcoders.arquitectomarvel.ui.main.mainActivityDi

import com.architectcoders.arquitectomarvel.ui.main.MainViewModel
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.GetCharacters
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun getCharacterProvider(characterRepository: CharacterRepository): GetCharacters =
        GetCharacters(characterRepository)

    @Provides
    fun mainViewModelProvider(getCharacters: GetCharacters): MainViewModel =
        MainViewModel(getCharacters)
}

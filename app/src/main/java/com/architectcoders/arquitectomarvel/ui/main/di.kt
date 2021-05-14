package com.architectcoders.arquitectomarvel.ui.main

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.usescases.UseCaseGetCharactersRemote
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule {

    @Provides
    fun useCaseGetCharactersRemoteProvide(
        marvelRepository: MarvelRepository
    ): UseCaseGetCharactersRemote = UseCaseGetCharactersRemote(marvelRepository)

    @Provides
    fun mainViewModelProvider(useCaseGetCharactersRemote: UseCaseGetCharactersRemote): MainViewModel =
        MainViewModel(useCaseGetCharactersRemote)
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}
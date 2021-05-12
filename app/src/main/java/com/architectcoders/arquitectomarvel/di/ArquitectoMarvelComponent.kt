package com.architectcoders.arquitectomarvel.di

import android.app.Application
import com.architectcoders.arquitectomarvel.ui.detail.characterDetailActivityDi.CharacterDetailActivityComponent
import com.architectcoders.arquitectomarvel.ui.detail.characterDetailActivityDi.CharacterDetailActivityModule
import com.architectcoders.arquitectomarvel.ui.main.mainActivityDi.MainActivityComponent
import com.architectcoders.arquitectomarvel.ui.main.mainActivityDi.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface ArquitectoMarvelComponent {

    fun plus(mainActivityModule: MainActivityModule): MainActivityComponent
    fun plus(characterDetailActivityModule: CharacterDetailActivityModule): CharacterDetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ArquitectoMarvelComponent
    }
}

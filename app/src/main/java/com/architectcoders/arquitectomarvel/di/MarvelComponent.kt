package com.architectcoders.arquitectomarvel.di

import android.app.Application
import com.architectcoders.arquitectomarvel.ui.detail.di.CharacterDetailActivityComponent
import com.architectcoders.arquitectomarvel.ui.detail.di.CharacterDetailActivityModule
import com.architectcoders.arquitectomarvel.ui.main.di.MainActivityModule
import com.architectcoders.arquitectomarvel.ui.main.di.MainActivityComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MarvelComponent {

    fun add(module: MainActivityModule): MainActivityComponent
    fun add(module: CharacterDetailActivityModule): CharacterDetailActivityComponent


    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app: Application): MarvelComponent
    }
}
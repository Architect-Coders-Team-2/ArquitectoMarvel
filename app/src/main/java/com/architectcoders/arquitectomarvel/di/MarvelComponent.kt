package com.architectcoders.arquitectomarvel.di

import android.app.Application
import com.architectcoders.arquitectomarvel.ui.detail.HeroDatailActivityModule
import com.architectcoders.arquitectomarvel.ui.detail.HeroDetailActivityComponent
import com.architectcoders.arquitectomarvel.ui.main.MainActivityComponent
import com.architectcoders.arquitectomarvel.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MarvelComponent {

    fun plus(module: MainActivityModule): MainActivityComponent

    fun plus(module: HeroDatailActivityModule): HeroDetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MarvelComponent
    }
}
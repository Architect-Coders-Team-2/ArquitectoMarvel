package com.architectcoders.arquitectomarvel

import android.app.Application
import com.architectcoders.arquitectomarvel.data.database.CharacterDatabase
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.data.server.MarvelDataSource
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailActivity
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import com.architectcoders.arquitectomarvel.ui.main.MainActivity
import com.architectcoders.arquitectomarvel.ui.main.MainViewModel
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.usecases.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { BuildConfig.MARVEL_API_KEY }
    single { CharacterDatabase.getInstance(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { MarvelDataSource() }
}

private val dataModule = module {
    factory { CharacterRepository(get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
        scoped { GetCharacters(get()) }
    }

    scope(named<CharacterDetailActivity>()) {
        viewModel { (characterId: Int) ->
            CharacterDetailViewModel(
                characterId,
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
        scoped { GetCharacterById(get()) }
        scoped { IsCharacterFavorite(get()) }
        scoped { GetComicsFromCharacterId(get()) }
        scoped { InsertFavoriteCharacter(get()) }
        scoped { InsertFavoriteComic(get()) }
        scoped { DeleteFavoriteCharacter(get()) }
        scoped { DeleteFavoriteComic(get()) }
    }
}

package com.architectcoders.arquitectomarvel.ui.detail

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.usescases.UseCaseDeleteFavoriteCharacter
import com.architectcoders.module.usescases.UseCaseGetComicsRemote
import com.architectcoders.module.usescases.UseCaseInsertFavoriteCharacter
import com.architectcoders.module.usescases.UseCaseIsCharacterFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class HeroDatailActivityModule {

    @Provides
    fun useCaseGetComicsRemoteProvide(
        marvelRepository: MarvelRepository
    ): UseCaseGetComicsRemote = UseCaseGetComicsRemote(marvelRepository)

    @Provides
    fun useCaseInsertFavoriteCharacterProvide(
        marvelRepository: MarvelRepository
    ): UseCaseInsertFavoriteCharacter = UseCaseInsertFavoriteCharacter(marvelRepository)

    @Provides
    fun useCaseIsCharacterFavoriteProvide(
        marvelRepository: MarvelRepository
    ): UseCaseIsCharacterFavorite = UseCaseIsCharacterFavorite(marvelRepository)

    @Provides
    fun useCaseDeleteFavoriteCharacterProvide(
        marvelRepository: MarvelRepository
    ): UseCaseDeleteFavoriteCharacter = UseCaseDeleteFavoriteCharacter(marvelRepository)

    @Provides
    fun heroDetailViewModelProvider(
        useCaseGetComicsRemote: UseCaseGetComicsRemote,
        useCaseInsertFavoriteCharacter: UseCaseInsertFavoriteCharacter,
        useCaseIsCharacterFavorite: UseCaseIsCharacterFavorite,
        useCaseDeleteFavoriteCharacter: UseCaseDeleteFavoriteCharacter
    ): HeroDetailViewModel =
        HeroDetailViewModel(
            useCaseGetComicsRemote,
            useCaseInsertFavoriteCharacter,
            useCaseIsCharacterFavorite,
            useCaseDeleteFavoriteCharacter
        )
}

@Subcomponent(modules = [(HeroDatailActivityModule::class)])
interface HeroDetailActivityComponent {
    val heroDetailViewModel: HeroDetailViewModel
}
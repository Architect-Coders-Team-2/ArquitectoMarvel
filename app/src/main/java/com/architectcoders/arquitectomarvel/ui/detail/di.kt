package com.architectcoders.arquitectomarvel.ui.detail

import com.architectcoders.module.data.MarvelRepository
import com.architectcoders.module.usescases.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class HeroDatailActivityModule {

    @Provides
    fun UseCaseGetComicsRemoteProvide(
        marvelRepository: MarvelRepository
    ): UseCaseGetComicsRemote = UseCaseGetComicsRemote(marvelRepository)

    @Provides
    fun UseCaseInsertFavoriteCharacterProvide(
        marvelRepository: MarvelRepository
    ): UseCaseInsertFavoriteCharacter = UseCaseInsertFavoriteCharacter(marvelRepository)

    @Provides
    fun UseCaseInsertFavoriteComicProvide(
        marvelRepository: MarvelRepository
    ): UseCaseInsertFavoriteComic = UseCaseInsertFavoriteComic(marvelRepository)

    @Provides
    fun UseCaseIsCharacterFavoriteProvide(
        marvelRepository: MarvelRepository
    ): UseCaseIsCharacterFavorite = UseCaseIsCharacterFavorite(marvelRepository)

    @Provides
    fun UseCaseDeleteFavoriteCharacterProvide(
        marvelRepository: MarvelRepository
    ): UseCaseDeleteFavoriteCharacter = UseCaseDeleteFavoriteCharacter(marvelRepository)

    @Provides
    fun UseCaseDeleteFavoriteDetailComicProvide(
        marvelRepository: MarvelRepository
    ): UseCaseDeleteFavoriteDetailComic = UseCaseDeleteFavoriteDetailComic(marvelRepository)

    @Provides
    fun heroDetailViewModelProvider(
        useCaseGetComicsRemote: UseCaseGetComicsRemote,
        useCaseInsertFavoriteCharacter: UseCaseInsertFavoriteCharacter,
        useCaseInsertFavoriteComic: UseCaseInsertFavoriteComic,
        useCaseIsCharacterFavorite: UseCaseIsCharacterFavorite,
        useCaseDeleteFavoriteCharacter: UseCaseDeleteFavoriteCharacter,
        useCaseDeleteFavoriteDetailComic: UseCaseDeleteFavoriteDetailComic,
    ): HeroDetailViewModel =
        HeroDetailViewModel(
            useCaseGetComicsRemote,
            useCaseInsertFavoriteCharacter,
            useCaseInsertFavoriteComic,
            useCaseIsCharacterFavorite,
            useCaseDeleteFavoriteCharacter,
            useCaseDeleteFavoriteDetailComic
        )
}

@Subcomponent(modules = [(HeroDatailActivityModule::class)])
interface HeroDetailActivityComponent {
    val heroDetailViewModel: HeroDetailViewModel
}
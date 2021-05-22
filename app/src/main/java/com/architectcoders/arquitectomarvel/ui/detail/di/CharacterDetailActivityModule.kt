package com.architectcoders.arquitectomarvel.ui.detail.di

import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides

@Module
class CharacterDetailActivityModule(private val id: Int) {

    @Provides
    fun detailViewModelProvides(
            getCharacterById: GetCharacterById,
            isCharacterFavorite: IsCharacterFavorite,
            getComicsFromCharacterId: GetComicsFromCharacterId,
            insertFavoriteCharacter: InsertFavoriteCharacter,
            insertFavoriteComic: InsertFavoriteComic,
            deleteFavoriteCharacter: DeleteFavoriteCharacter,
            deleteFavoriteComic: DeleteFavoriteComic
    ): CharacterDetailViewModel =
          CharacterDetailViewModel(
            id,
            getCharacterById,
            isCharacterFavorite,
            getComicsFromCharacterId,
            insertFavoriteCharacter,
            insertFavoriteComic,
            deleteFavoriteCharacter,
            deleteFavoriteComic
        )


    @Provides
    fun getCharacterByIdProvider(repository: CharacterRepository): GetCharacterById =
        GetCharacterById(repository)

    @Provides
    fun isCharacterFavoriteProvider(repository: CharacterRepository): IsCharacterFavorite =
        IsCharacterFavorite(repository)

    @Provides
    fun getComicsFromCharacterIdProvider(repository: CharacterRepository): GetComicsFromCharacterId =
        GetComicsFromCharacterId(repository)

    @Provides
    fun insertFavoriteCharacterProvider(repository: CharacterRepository): InsertFavoriteCharacter =
        InsertFavoriteCharacter(repository)

    @Provides
    fun insertFavoriteComicProvider(repository: CharacterRepository): InsertFavoriteComic =
        InsertFavoriteComic(repository)

    @Provides
    fun deleteFavoriteCharacterProvider(repository: CharacterRepository): DeleteFavoriteCharacter =
        DeleteFavoriteCharacter(repository)

    @Provides
    fun deleteFavoriteComicProvider(repository: CharacterRepository): DeleteFavoriteComic =
        DeleteFavoriteComic(repository)


}
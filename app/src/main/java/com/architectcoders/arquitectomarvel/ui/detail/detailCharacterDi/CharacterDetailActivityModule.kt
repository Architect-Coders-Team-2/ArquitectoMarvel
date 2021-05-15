package com.architectcoders.arquitectomarvel.ui.detail.detailCharacterDi

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CharacterDetailActivityModule {

    @Provides
    fun getCharacterByIdProvider(characterRepository: CharacterRepository): GetCharacterById =
        GetCharacterById(characterRepository)

    @Provides
    fun isCharacterFavoriteProvider(characterRepository: CharacterRepository): IsCharacterFavorite =
        IsCharacterFavorite(characterRepository)

    @Provides
    fun getComicsFromCharacterIdProvider(characterRepository: CharacterRepository): GetComicsFromCharacterId =
        GetComicsFromCharacterId(characterRepository)

    @Provides
    fun insertFavoriteCharacterProvider(characterRepository: CharacterRepository): InsertFavoriteCharacter =
        InsertFavoriteCharacter(characterRepository)

    @Provides
    fun insertFavoriteComicProvider(characterRepository: CharacterRepository): InsertFavoriteComic =
        InsertFavoriteComic(characterRepository)

    @Provides
    fun deleteFavoriteCharacterProvider(characterRepository: CharacterRepository): DeleteFavoriteCharacter =
        DeleteFavoriteCharacter(characterRepository)

    @Provides
    fun deleteFavoriteComicProvider(characterRepository: CharacterRepository): DeleteFavoriteComic =
        DeleteFavoriteComic(characterRepository)
}

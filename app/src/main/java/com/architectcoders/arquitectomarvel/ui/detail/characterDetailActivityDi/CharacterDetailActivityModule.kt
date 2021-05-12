package com.architectcoders.arquitectomarvel.ui.detail.characterDetailActivityDi

import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.*
import dagger.Module
import dagger.Provides

@Module
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

    @Provides
    fun characterDetailViewModelProvider(
        getCharacterById: GetCharacterById,
        isCharacterFavorite: IsCharacterFavorite,
        getComicsFromCharacterId: GetComicsFromCharacterId,
        insertFavoriteCharacter: InsertFavoriteCharacter,
        insertFavoriteComic: InsertFavoriteComic,
        deleteFavoriteCharacter: DeleteFavoriteCharacter,
        deleteFavoriteComic: DeleteFavoriteComic
    ): CharacterDetailViewModel =
        CharacterDetailViewModel(
            getCharacterById,
            isCharacterFavorite,
            getComicsFromCharacterId,
            insertFavoriteCharacter,
            insertFavoriteComic,
            deleteFavoriteCharacter,
            deleteFavoriteComic
        )
}

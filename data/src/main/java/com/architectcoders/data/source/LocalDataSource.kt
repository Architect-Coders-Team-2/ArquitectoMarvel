package com.architectcoders.data.source

import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

interface LocalDataSource {
    suspend fun insertFavoriteHero(favouriteCharacter: CharacterResult)
    suspend fun deleteFavoriteHero(favouriteCharacter: CharacterResult)
    suspend fun isHeroFavorite(characterId: Int): Boolean
    suspend fun insertFavoriteComic(comic: ComicResult)
    suspend fun deleteFavoriteComic(comic: ComicResult)
}

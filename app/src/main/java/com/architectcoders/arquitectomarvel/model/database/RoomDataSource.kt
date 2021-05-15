package com.architectcoders.arquitectomarvel.model.database

import com.architectcoders.data.source.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult


class RoomDataSource(db: ResultDatabase) : LocalDataSource {

    private val characterDao = db.resultDao

    override suspend fun insertFavoriteHero(favouriteCharacter: CharacterResult) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteCharacter(favouriteCharacter.toCharacterEntity)
        }

    override suspend fun deleteFavoriteHero(favouriteCharacter: CharacterResult) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteCharacter(favouriteCharacter.toCharacterEntity)
        }

    override suspend fun isHeroFavorite(characterId: Int): Boolean =
        withContext(Dispatchers.IO) {
            characterDao.isCharacterFavorite(characterId) != null
        }

    override suspend fun insertFavoriteComic(comic: ComicResult) {
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteComic(comic.toComicEntity)
        }
    }

    override suspend fun deleteFavoriteComic(comic: ComicResult) {
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteComic(comic.toComicEntity)
        }
    }
}
package com.architectcoders.arquitectomarvel.data.database

import com.architectcoders.arquitectomarvel.data.database.models.toCharacterEntity
import com.architectcoders.arquitectomarvel.data.database.models.toComicEntity
import com.architectcoders.data.source.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class RoomDataSource(db: CharacterDatabase) : LocalDataSource {

    private val characterDao = db.characterDao

    override suspend fun insertFavoriteCharacter(favouriteCharacter: CharacterResult) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteCharacter(favouriteCharacter.toCharacterEntity)
        }

    override suspend fun deleteFavoriteCharacter(favouriteCharacter: CharacterResult) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteCharacter(favouriteCharacter.toCharacterEntity)
        }

    override suspend fun isCharacterFavorite(characterId: Int): Boolean =
        withContext(Dispatchers.IO) {
            characterDao.isCharacterFavorite(characterId) != null
        }

    override suspend fun insertFavoriteDetailedComic(comic: ComicResult) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteComic(comic.toComicEntity)
        }

    override suspend fun deleteFavoriteDetailedComic(comic: ComicResult) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteComic(comic.toComicEntity)
        }
}

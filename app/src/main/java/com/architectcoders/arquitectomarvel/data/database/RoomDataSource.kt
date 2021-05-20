package com.architectcoders.arquitectomarvel.data.database

import com.architectcoders.data.source.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class RoomDataSource(db: MarvelDatabase) : LocalDataSource {

    private val characterDao = db.marvelDao

    override suspend fun getLocalCharacters(): List<CharacterResult> =
        withContext(Dispatchers.IO) {
            characterDao.getLocalCharacters().toDomainCharacterList
        }

    override suspend fun insertFavoriteCharacter(vararg param: Any) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteCharacter((param.first() as CharacterResult).toCharacterEntity)
        }

    override suspend fun deleteFavoriteCharacter(vararg param: Any) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteCharacter((param.first() as CharacterResult).toCharacterEntity)
        }

    override suspend fun isCharacterFavorite(vararg param: Any): Boolean =
        withContext(Dispatchers.IO) {
            characterDao.isCharacterFavorite(param.first() as Int) != null
        }

    override suspend fun insertFavoriteDetailedComic(vararg param: Any) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteComic((param.first() as ComicResult).toComicEntity)
        }

    override suspend fun deleteFavoriteDetailedComic(vararg param: Any) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteComic((param.first() as ComicResult).toComicEntity)
        }
}

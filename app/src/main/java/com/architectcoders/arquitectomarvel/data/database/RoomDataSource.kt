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

    override suspend fun insertFavoriteDetailedComic(favouriteComic: ComicResult) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteComic(favouriteComic.toComicEntity)
        }

    override suspend fun deleteFavoriteDetailedComic(favouriteComic: ComicResult) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteComic(favouriteComic.toComicEntity)
        }
}

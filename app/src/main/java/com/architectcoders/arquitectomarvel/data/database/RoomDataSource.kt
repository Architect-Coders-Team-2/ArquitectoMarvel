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

    override suspend fun insertFavoriteCharacter(favoriteCharacter: CharacterResult) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteCharacter(favoriteCharacter.toCharacterEntity)
        }

    override suspend fun deleteFavoriteCharacter(favoriteCharacter: CharacterResult) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteCharacter(favoriteCharacter.toCharacterEntity)
        }

    override suspend fun isCharacterFavorite(characterId: Int): Boolean =
        withContext(Dispatchers.IO) {
            characterDao.isCharacterFavorite(characterId) != null
        }

    override suspend fun insertFavoriteDetailedComic(favoriteComic: ComicResult) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteComic(favoriteComic.toComicEntity)
        }

    override suspend fun deleteFavoriteDetailedComic(favoriteComic: ComicResult) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteComic(favoriteComic.toComicEntity)
        }
}

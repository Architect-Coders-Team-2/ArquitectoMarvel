package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.data.source.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class RoomDataSource(db: MarvelDatabase) : LocalDataSource {

    private val characterDao = db.marvelDao

    override suspend fun getLocalCharacters(): List<CharacterResult>? =
        withContext(Dispatchers.IO) {
            characterDao.getLocalCharacters()?.toDomainCharacterList
        }

    override suspend fun getLocalCharacterById(characterId: Int): CharacterResult =
        withContext(Dispatchers.IO) {
            characterDao.getLocalCharacterById(characterId).toDomainCharacter
        }

    override suspend fun getLastTimeStamp(): Long? =
        withContext(Dispatchers.IO) {
            characterDao.getLastTimeStamp()
        }

    override suspend fun getStoredCharactersCount(): Int =
        withContext(Dispatchers.IO) {
            characterDao.getStoredCharactersCount() ?: 0
        }

    override suspend fun insertAllCharacters(characterList: List<com.architectcoders.domain.characters.Result>) =
        withContext(Dispatchers.IO) {
            characterDao.insertAllCharacters(characterList.toCharacterEntityList)
        }

    override suspend fun deleteAllCharacters() =
        withContext(Dispatchers.IO) {
            characterDao.deleteAllCharacters()
        }

    override suspend fun insertFavoriteCharacter(favoriteCharacter: CharacterResult) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)
        }

    override suspend fun deleteFavoriteCharacter(favoriteCharacter: CharacterResult) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)
        }

    override suspend fun isCharacterFavorite(characterId: Int): Boolean =
        withContext(Dispatchers.IO) {
            characterDao.isCharacterFavorite(characterId) != null
        }

    override suspend fun insertFavoriteDetailedComic(favoriteComic: ComicResult) =
        withContext(Dispatchers.IO) {
            characterDao.insertFavoriteComic(favoriteComic.toFavoriteComicEntity)
        }

    override suspend fun deleteFavoriteDetailedComic(favoriteComic: ComicResult) =
        withContext(Dispatchers.IO) {
            characterDao.deleteFavoriteComic(favoriteComic.toFavoriteComicEntity)
        }

    override fun getPagingSource(): PagingSource<Int, CharacterEntity> =
        characterDao.getPagingSource()
}

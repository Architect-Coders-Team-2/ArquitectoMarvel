package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class RoomDataSource(db: MarvelDatabase) : LocalDataSource {

    private val characterDao = db.marvelDao

    override suspend fun getLocalCharacters(): List<CharacterResult>? =
        characterDao.getLocalCharacters()?.toDomainCharacterList

    override suspend fun getLastTimeStamp(): Long? =
        characterDao.getLastTimeStamp()

    override suspend fun getStoredCharactersCount(): Int =
        characterDao.getStoredCharactersCount() ?: 0

    override suspend fun insertAllCharacters(characterList: List<com.architectcoders.domain.characters.Result>) =
        characterDao.insertAllCharacters(characterList.toCharacterEntityList)

    override suspend fun deleteAllCharacters() =
        characterDao.deleteAllCharacters()

    override suspend fun insertFavoriteCharacter(favoriteCharacter: CharacterResult) =
        characterDao.insertFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun deleteFavoriteCharacter(favoriteCharacter: CharacterResult) =
        characterDao.deleteFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun isCharacterFavorite(characterId: Int): Boolean =
        characterDao.isCharacterFavorite(characterId) != null

    override suspend fun insertFavoriteDetailedComic(favoriteComic: ComicResult) =
        characterDao.insertFavoriteComic(favoriteComic.toFavoriteComicEntity)

    override suspend fun deleteFavoriteDetailedComic(favoriteComic: ComicResult) =
        characterDao.deleteFavoriteComic(favoriteComic.toFavoriteComicEntity)

    override fun getPagingSource(): PagingSource<Int, CharacterEntity> =
        characterDao.getPagingSource()
}

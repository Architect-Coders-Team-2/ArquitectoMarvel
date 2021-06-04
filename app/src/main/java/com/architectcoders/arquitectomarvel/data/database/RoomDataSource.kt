package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.characters.Hero as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class RoomDataSource(db: MarvelDatabase) : LocalDataSource {

    private val characterDao = db.marvelDao

    override suspend fun getLocalCharacterById(characterId: Int): CharacterResult =
        characterDao.getLocalCharacterById(characterId).toDomainCharacter

    override suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        characterDao.getLastTimeStampFromCharacterEntity()

    override suspend fun getLocalCharactersCount(): Int =
        characterDao.getLocalCharactersCount() ?: 0

    override suspend fun insertAllLocalCharacters(characterList: List<com.architectcoders.domain.characters.Hero>) =
        characterDao.insertAllLocalCharacters(characterList.toCharacterEntityList)

    override suspend fun deleteAllLocalCharacters() =
        characterDao.deleteAllLocalCharacters()

    override suspend fun insertLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        characterDao.insertLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        characterDao.deleteLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun isLocalCharacterFavorite(characterId: Int): Boolean =
        characterDao.isLocalCharacterFavorite(characterId) != null

    override suspend fun insertLocalFavoriteComic(favoriteComic: ComicResult) =
        characterDao.insertLocalFavoriteComic(favoriteComic.toFavoriteComicEntity)

    override suspend fun deleteLocalFavoriteComic(favoriteComic: ComicResult) =
        characterDao.deleteLocalFavoriteComic(favoriteComic.toFavoriteComicEntity)

    override fun getPagingSourceFromCharacterEntity(): PagingSource<Int, CharacterEntity> =
        characterDao.getPagingSourceFromCharacterEntity()
}

package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic

class RoomDataSource(db: MarvelDatabase) : LocalDataSource {

    private val characterDao = db.marvelDao

    override suspend fun getLocalCharacterById(characterId: Int): Character =
        characterDao.getLocalCharacterById(characterId).toDomainCharacter

    override suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        characterDao.getLastTimeStampFromCharacterEntity()

    override suspend fun getLocalCharactersCount(): Int =
        characterDao.getLocalCharactersCount() ?: 0

    override suspend fun insertAllLocalCharacters(characterList: List<Character>) =
        characterDao.insertAllLocalCharacters(characterList.toCharacterEntityList)

    override suspend fun deleteAllLocalCharacters() =
        characterDao.deleteAllLocalCharacters()

    override suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character) =
        characterDao.insertLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character) =
        characterDao.deleteLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun isLocalCharacterFavorite(characterId: Int): Boolean =
        characterDao.isLocalCharacterFavorite(characterId) != null

    override suspend fun insertLocalFavoriteComic(favoriteComic: Comic) =
        characterDao.insertLocalFavoriteComic(favoriteComic.toComicEntity)

    override suspend fun deleteLocalFavoriteComic(favoriteComic: Comic) =
        characterDao.deleteLocalFavoriteComic(favoriteComic.toComicEntity)

    override fun getPagingSourceFromCharacterEntity(): PagingSource<Int, CharacterEntity> =
        characterDao.getPagingSourceFromCharacterEntity()
}

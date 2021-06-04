package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.comics.Comic as ComicResult
import com.architectcoders.domain.heros.Hero as CharacterResult

class RoomDataSource(db: MarvelDatabase) : LocalDataSource {

    private val characterDao = db.marvelDao

    override suspend fun getLocalCharacterById(characterId: Int): CharacterResult =
        characterDao.getLocalCharacterById(characterId).toDomainCharacter

    override suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        characterDao.getLastTimeStampFromCharacterEntity()

    override suspend fun getLocalCharactersCount(): Int =
        characterDao.getLocalCharactersCount() ?: 0

    override suspend fun insertAllLocalCharacters(characterList: List<com.architectcoders.domain.heros.Hero>) =
        characterDao.insertAllLocalCharacters(characterList.toHeroEntityList)

    override suspend fun deleteAllLocalCharacters() =
        characterDao.deleteAllLocalCharacters()

    override suspend fun insertLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        characterDao.insertLocalFavoriteCharacter(favoriteCharacter.toFavoriteHeroEntity)

    override suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        characterDao.deleteLocalFavoriteCharacter(favoriteCharacter.toFavoriteHeroEntity)

    override suspend fun isLocalCharacterFavorite(characterId: Int): Boolean =
        characterDao.isLocalCharacterFavorite(characterId) != null

    override suspend fun insertLocalFavoriteComic(favoriteComic: ComicResult) =
        characterDao.insertLocalFavoriteComic(favoriteComic.toComicEntity)

    override suspend fun deleteLocalFavoriteComic(favoriteComic: ComicResult) =
        characterDao.deleteLocalFavoriteComic(favoriteComic.toComicEntity)

    override fun getPagingSourceFromCharacterEntity(): PagingSource<Int, HeroEntity> =
        characterDao.getPagingSourceFromCharacterEntity()
}

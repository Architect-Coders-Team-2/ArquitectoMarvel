package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.comics.Comic
import kotlinx.coroutines.flow.Flow
import com.architectcoders.domain.characters.Result as CharacterResult
import com.architectcoders.domain.comics.Result as ComicResult

class RoomDataSource(db: MarvelDatabase) : LocalDataSource {

    private val marvelDao = db.marvelDao

    override suspend fun getLocalCharacterById(characterId: Int): CharacterResult =
        marvelDao.getLocalCharacterById(characterId).toDomainCharacter

    override suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        marvelDao.getLastTimeStampFromCharacterEntity()

    override suspend fun getLocalCharactersCount(): Int =
        marvelDao.getLocalCharactersCount() ?: 0

    override suspend fun insertAllLocalCharacters(characterList: List<com.architectcoders.domain.characters.Result>) =
        marvelDao.insertAllLocalCharacters(characterList.toCharacterEntityList)

    override suspend fun deleteAllLocalCharacters() =
        marvelDao.deleteAllLocalCharacters()

    override suspend fun insertLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        marvelDao.insertLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: CharacterResult) =
        marvelDao.deleteLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun isLocalCharacterFavorite(characterId: Int): Boolean =
        marvelDao.isLocalCharacterFavorite(characterId) != null

    override suspend fun deleteLocalFavoriteComic(idHero: Int) =
        marvelDao.deleteComicsForHero(idHero)

    override fun getPagingSourceFromCharacterEntity(): PagingSource<Int, CharacterEntity> =
        marvelDao.getPagingSourceFromCharacterEntity()

    override suspend fun fetchComicsForHero(map: Map<String, Any>) {
        marvelDao.fetchComicsForHero(map)
    }

    override fun getComicsForHero(idHero: Int): Any {
        return marvelDao.selecetComicsForHero(idHero)
    }
}

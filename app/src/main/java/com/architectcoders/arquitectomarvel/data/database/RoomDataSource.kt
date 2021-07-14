package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val characterDao: MarvelDao) : LocalDataSource {


    override suspend fun getLocalCharacterById(characterId: Int): Character =
        characterDao.getLocalCharacterById(characterId).toDomainCharacter

    override suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        characterDao.getLastTimeStampFromCharacterEntity()

    override suspend fun getLocalCharactersCount(): Int =
        characterDao.getLocalCharactersCount()

    override suspend fun insertAllLocalCharacters(characterList: List<Character>) =
        characterDao.insertAllLocalCharacters(characterList.toCharacterEntityList)

    override suspend fun deleteAllLocalCharacters() =
        characterDao.deleteAllLocalCharacters()

    override suspend fun getLocalFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>> =
        characterDao.getLocalFavoriteCharacters()

    override suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character) =
        characterDao.insertLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character) =
        characterDao.deleteLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override fun isLocalCharacterFavorite(characterId: Int): Flow<Int> =
        characterDao.isLocalCharacterFavorite(characterId)

    override fun getPagingSourceFromCharacterEntity(): PagingSource<Int, CharacterEntity> =
        characterDao.getPagingSourceFromCharacterEntity()

    override suspend fun fetchComicsForCharacter(map: Map<String, Any>) {
        characterDao.fetchComicsForCharacter(map)
    }

    override fun getComicsForCharacter(characterId: Int): Any {
        return characterDao.selectComicsForCharacter(characterId)
    }
}

package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao {

    @Query("SELECT * FROM characterentity ORDER BY name")
    fun getPagingSourceFromCharacterEntity(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE id = :characterId")
    suspend fun getLocalCharacterById(characterId: Int): CharacterEntity

    @Query("SELECT insertDate FROM characterentity LIMIT 1")
    suspend fun getLastTimeStampFromCharacterEntity(): Long?

    @Query("SELECT COUNT(id) FROM characterentity")
    suspend fun getLocalCharactersCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocalCharacters(characterList: List<CharacterEntity>)

    @Query("SELECT * FROM favoritecharacterentity ORDER BY name")
    fun getLocalFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalFavoriteComic(comicEntity: ComicEntity)

    @Delete
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Delete
    suspend fun deleteLocalFavoriteComic(comicEntity: ComicEntity)

    @Query("SELECT COUNT(id) FROM favoritecharacterentity WHERE id = :id")
    fun isLocalCharacterFavorite(id: Int): Flow<Int>

    @Query("DELETE FROM characterentity")
    suspend fun deleteAllLocalCharacters()
}

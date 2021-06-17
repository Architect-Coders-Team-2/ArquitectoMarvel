package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import androidx.room.*

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalFavoriteComic(comicEntity: ComicEntity)

    @Delete
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Delete
    suspend fun deleteLocalFavoriteComic(comicEntity: ComicEntity)

    @Query("SELECT id FROM favoritecharacterentity WHERE id = :id")
    suspend fun isLocalCharacterFavorite(id: Int): Int?

    @Query("DELETE FROM characterentity")
    suspend fun deleteAllLocalCharacters()
}

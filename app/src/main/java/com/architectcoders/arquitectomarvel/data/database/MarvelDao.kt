package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.architectcoders.arquitectomarvel.data.database.relations.CharacterWithComics

@Dao
interface MarvelDao {

    @Query("SELECT * FROM characterentity ORDER BY name")
    suspend fun getLocalCharacters(): List<CharacterEntity>?

    @Query("SELECT * FROM characterentity WHERE id = :characterId")
    suspend fun getLocalCharacterById(characterId: Int): CharacterEntity

    @Query("SELECT insertDate FROM characterentity LIMIT 1")
    suspend fun getLastTimeStamp(): Long?

    @Query("SELECT COUNT(id) FROM characterentity")
    suspend fun getStoredCharactersCount(): Int?

    @Transaction
    @Query("SELECT * FROM characterentity WHERE comicCollectionUri = :comicCollectionUri")
    suspend fun getCharacterWithComics(comicCollectionUri: String): List<CharacterWithComics>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characterList: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteComic(favoriteComicEntity: FavoriteComicEntity)

    @Delete
    suspend fun deleteFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Delete
    suspend fun deleteFavoriteComic(favoriteComicEntity: FavoriteComicEntity)

    @Query("SELECT id FROM favoritecharacterentity WHERE id = :id")
    suspend fun isCharacterFavorite(id: Int): Int?

    @Query("SELECT * FROM characterentity ORDER BY name")
    fun getPagingSource(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characterentity")
    suspend fun deleteAllCharacters()
}

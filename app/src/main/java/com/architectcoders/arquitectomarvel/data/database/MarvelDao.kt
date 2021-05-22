package com.architectcoders.arquitectomarvel.data.database

import androidx.room.*
import com.architectcoders.arquitectomarvel.data.database.relations.CharacterWithComics

@Dao
interface MarvelDao {

    @Query("SELECT * FROM characterentity ORDER BY name")
    suspend fun getLocalCharacters(): List<CharacterEntity>

    @Transaction
    @Query("SELECT * FROM characterentity WHERE comicCollectionUri = :comicCollectionUri")
    suspend fun getCharacterWithComics(comicCollectionUri: String): List<CharacterWithComics>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCharacter(characterEntity: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteComic(comicEntity: ComicEntity)

    @Delete
    suspend fun deleteFavoriteCharacter(characterEntity: CharacterEntity)

    @Delete
    suspend fun deleteFavoriteComic(comicEntity: ComicEntity)

    @Query("SELECT id FROM characterentity WHERE id = :id")
    suspend fun isCharacterFavorite(id: Int): Int?
}

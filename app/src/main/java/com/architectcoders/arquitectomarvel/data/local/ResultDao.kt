package com.architectcoders.arquitectomarvel.data.local

import androidx.room.*
import com.architectcoders.arquitectomarvel.data.local.entities.CharacterEntity
import com.architectcoders.arquitectomarvel.data.local.entities.DetailedComicEntity

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCharacter(characterEntity: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteDetailedComic(detailedComicEntity: DetailedComicEntity)

    @Delete
    suspend fun deleteFavoriteCharacter(characterEntity: CharacterEntity)

    @Delete
    suspend fun deleteFavoriteDetailedComic(detailedComicEntity: DetailedComicEntity)

    @Query("SELECT id FROM characterentity WHERE id = :id")
    suspend fun isCharacterFavorite(id: Int): Int?
}

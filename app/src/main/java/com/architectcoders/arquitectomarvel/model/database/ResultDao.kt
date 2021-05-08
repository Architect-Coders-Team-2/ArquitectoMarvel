package com.architectcoders.arquitectomarvel.model.database

import androidx.room.*
import com.architectcoders.arquitectomarvel.model.database.relations.CharacterWithComics
import com.architectcoders.arquitectomarvel.model.database.relations.ResultWithItemsComics

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(resultRoom: ResultRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComics(itemComics: ItemComics)

    @Query("SELECT * FROM resultroom ORDER BY name")
    suspend fun getResults(): List<ResultRoom>

    @Transaction
    @Query("SELECT * FROM resultRoom WHERE comicsCollectionURI = :comicsCollectionURI")
    suspend fun getResultWithItemsComics(comicsCollectionURI: String): List<ResultWithItemsComics>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCharacter(characterEntity: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteDetailedComic(detailedComicEntity: DetailedComicEntity)

    @Delete
    suspend fun deleteFavoriteCharacter(characterEntity: CharacterEntity)

    @Delete
    suspend fun deleteFavoriteDetailedComic(detailedComicEntity: DetailedComicEntity)

    @Transaction
    @Query("SELECT * FROM characterentity WHERE comicCollectionUri = :comicCollectionUri")
    suspend fun getCharacterWithComics(comicCollectionUri: String): List<CharacterWithComics>

    @Query("SELECT * FROM characterentity WHERE id = :id")
    suspend fun isCharacterFavorite(id: Int): List<CharacterEntity>
}

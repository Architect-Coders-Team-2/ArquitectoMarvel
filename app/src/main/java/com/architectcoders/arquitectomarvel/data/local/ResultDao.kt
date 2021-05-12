package com.architectcoders.arquitectomarvel.data.local

import androidx.room.*
import com.architectcoders.arquitectomarvel.data.local.entities.CharacterEntity
import com.architectcoders.arquitectomarvel.data.local.entities.DetailedComicEntity
import com.architectcoders.arquitectomarvel.data.local.entities.ItemComics
import com.architectcoders.arquitectomarvel.data.local.entities.ResultRoom
import com.architectcoders.arquitectomarvel.data.local.entities.relations.CharacterWithComics
import com.architectcoders.arquitectomarvel.data.local.entities.relations.ResultWithItemsComics

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

    @Query("SELECT id FROM characterentity WHERE id = :id")
    suspend fun isCharacterFavorite(id: Int): List<CharacterEntity>
}

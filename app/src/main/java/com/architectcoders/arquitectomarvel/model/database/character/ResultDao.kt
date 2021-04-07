package com.architectcoders.arquitectomarvel.model.database.character

import androidx.room.*
import com.architectcoders.arquitectomarvel.model.database.character.relations.ResultWithItemsComics

@Dao
interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(resultRoom: ResultRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComics(itemComics: ItemComics)

    @Query("SELECT * FROM resultroom")
    suspend fun getResults(): List<ResultRoom>

    @Transaction
    @Query("SELECT * FROM resultRoom WHERE comicsCollectionURI = :comicsCollectionURI")
    suspend fun getResultWithItemsComics(comicsCollectionURI: String): List<ResultWithItemsComics>
}

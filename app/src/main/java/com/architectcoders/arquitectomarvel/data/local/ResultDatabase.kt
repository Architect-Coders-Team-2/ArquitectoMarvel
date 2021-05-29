package com.architectcoders.arquitectomarvel.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.architectcoders.arquitectomarvel.data.local.entities.CharacterEntity
import com.architectcoders.arquitectomarvel.data.local.entities.DetailedComicEntity
import com.architectcoders.arquitectomarvel.data.local.entities.ItemComics
import com.architectcoders.arquitectomarvel.data.local.entities.ResultRoom

@Database(
    entities = [
        ResultRoom::class,
        ItemComics::class,
        CharacterEntity::class,
        DetailedComicEntity::class
    ],
    version = 1
)
abstract class ResultDatabase : RoomDatabase() {

    abstract val resultDao: ResultDao

    companion object {
        @Volatile
        private var INSTANCE: ResultDatabase? = null

        fun getInstance(context: Context): ResultDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ResultDatabase::class.java,
                    "result_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

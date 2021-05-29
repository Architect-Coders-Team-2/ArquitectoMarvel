package com.architectcoders.arquitectomarvel.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.architectcoders.arquitectomarvel.data.database.models.CharacterEntity
import com.architectcoders.arquitectomarvel.data.database.models.ComicEntity

@Database(
    entities = [
        CharacterEntity::class,
        ComicEntity::class
    ],
    version = 1
)
abstract class CharacterDatabase : RoomDatabase() {

    abstract val characterDao: CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getInstance(context: Context): CharacterDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    "result_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

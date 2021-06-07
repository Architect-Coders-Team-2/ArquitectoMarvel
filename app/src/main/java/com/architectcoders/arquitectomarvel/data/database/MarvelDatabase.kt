package com.architectcoders.arquitectomarvel.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CharacterEntity::class,
        FavoriteCharacterEntity::class,
        ComicEntity::class
    ],
    version = 1
)
abstract class MarvelDatabase : RoomDatabase() {

    abstract val marvelDao: MarvelDao

    companion object {
        @Volatile
        private var INSTANCE: MarvelDatabase? = null

        fun getInstance(context: Context): MarvelDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MarvelDatabase::class.java,
                    "result_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

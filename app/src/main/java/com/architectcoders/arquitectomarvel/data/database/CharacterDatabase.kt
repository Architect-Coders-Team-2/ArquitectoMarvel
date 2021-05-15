package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CharacterEntity::class,
        ComicEntity::class
    ],
    version = 1
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}

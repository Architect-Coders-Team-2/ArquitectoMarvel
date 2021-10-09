package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CharacterEntity::class,
        FavoriteCharacterEntity::class,
        ComicEntity::class,
        CredentialsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract val marvelDao: MarvelDao
}

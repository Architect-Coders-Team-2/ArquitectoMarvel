package com.architectcoders.arquitectomarvel.data.local

import androidx.room.Database
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
    version = 1,
    exportSchema = false
)
abstract class ResultDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDao
}

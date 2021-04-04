package com.architectcoders.arquitectomarvel.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ResultRoom::class,
        ItemComics::class
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
                    context.applicationContext,
                    ResultDatabase::class.java,
                    "result_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
package com.architectcoders.arquitectomarvel.model.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Result
import com.architectcoders.domain.characters.Thumbnail

@Entity
data class ResultRoom(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    @Embedded
    val thumbnail: Thumbnail?, // path, extension
    val comicsCollectionURI: String, // foreign key
    val comicsAvailable: Int?
)

val Result.dbObject: ResultRoom
    get() =
        ResultRoom(
            id = id,
            name = name,
            thumbnail = thumbnail,
            comicsCollectionURI = comics.collectionURI,
            comicsAvailable = comics.available
        )

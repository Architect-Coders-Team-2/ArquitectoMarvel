package com.architectcoders.arquitectomarvel.model.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.characters.Thumbnail

@Entity
data class ResultRoom(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val name: String?,

    @Embedded
    val thumbnail: Thumbnail?, // path, extension

    // TODO: 1/4/21 check with team, if it is more readable
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

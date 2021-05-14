package com.architectcoders.arquitectomarvel.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Thumbnail

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

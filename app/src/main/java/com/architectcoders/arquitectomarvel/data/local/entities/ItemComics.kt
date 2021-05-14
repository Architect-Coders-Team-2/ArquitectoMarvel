package com.architectcoders.arquitectomarvel.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemComics(
    @PrimaryKey(autoGenerate = false)
    val resourceURI: String,
    val name: String?,
    val collectionURI: String?
)


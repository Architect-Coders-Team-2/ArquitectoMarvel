package com.architectcoders.arquitectomarvel.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Result

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val comicCollectionUri: String,
    val comicListAvailable: Int?
)

val Result.toCharacterEntity: CharacterEntity
    get() = CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        comicCollectionUri = comics.collectionURI,
        comicListAvailable = comics.available
    )

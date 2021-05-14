package com.architectcoders.arquitectomarvel.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Result
import com.architectcoders.module.domain.models.Characters.ResultCharacters

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

fun ResultCharacters.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnailDomain?.path}.${thumbnailDomain?.extension}",
        comicCollectionUri = charactersComics?.collectionURI?:"",
        comicListAvailable = charactersComics?.available
    )
}

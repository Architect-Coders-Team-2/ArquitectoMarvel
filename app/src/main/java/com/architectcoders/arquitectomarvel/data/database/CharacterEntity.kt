package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Hero
import com.architectcoders.domain.characters.Thumbnail

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val comicCollectionUri: String?,
    val comicListAvailable: Int?,
    val pageNumber: Int?,
    val insertDate: Long?
)

val List<Hero>.toCharacterEntityList: List<CharacterEntity>
    get() = map { it.toCharacterEntity }

val Hero.toCharacterEntity: CharacterEntity
    get() = CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = if (comicListAvailable) 1 else 0,
        pageNumber = pageNumber,
        insertDate = System.currentTimeMillis()
    )

val List<CharacterEntity>.toDomainCharacterList: List<Hero>
    get() = map { it.toDomainCharacter }

val CharacterEntity.toDomainCharacter: Hero
    get() = Hero(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail?.let {
            Thumbnail(
                it.substringBeforeLast("."),
                it.substringAfterLast(".")
            )
        },
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = comicListAvailable != null,
        pageNumber = pageNumber,
        insertDate = insertDate
    )

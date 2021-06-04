package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Hero
import com.architectcoders.domain.characters.Thumbnail

@Entity
data class FavoriteCharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val comicCollectionUri: String?,
    val comicListAvailable: Int?,
    val insertDate: Long?
)

val List<Hero>.toFavoriteCharacterEntityList: List<FavoriteCharacterEntity>
    get() = map { it.toFavoriteCharacterEntity }

val Hero.toFavoriteCharacterEntity: FavoriteCharacterEntity
    get() = FavoriteCharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = if (comicListAvailable) 1 else 0,
        insertDate = System.currentTimeMillis()
    )

val List<FavoriteCharacterEntity>.toDomainCharacterList: List<Hero>
    get() = map { it.toDomainCharacter }

val FavoriteCharacterEntity.toDomainCharacter: Hero
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
        pageNumber = null,
        insertDate = insertDate
    )

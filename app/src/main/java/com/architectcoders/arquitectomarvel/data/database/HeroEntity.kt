package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.Thumbnail
import com.architectcoders.domain.heros.Hero

@Entity
data class HeroEntity(
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

val List<Hero>.toHeroEntityList: List<HeroEntity>
    get() = map { it.toHeroEntity }

val Hero.toHeroEntity: HeroEntity
    get() = HeroEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = if (comicListAvailable) 1 else 0,
        pageNumber = pageNumber,
        insertDate = System.currentTimeMillis()
    )

val List<HeroEntity>.toDomainCharacterList: List<Hero>
    get() = map { it.toDomainCharacter }

val HeroEntity.toDomainCharacter: Hero
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

package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.Thumbnail
import com.architectcoders.domain.heros.Hero

@Entity
data class FavoriteHeroEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val comicCollectionUri: String?,
    val comicListAvailable: Int?,
    val insertDate: Long?
)

val List<Hero>.toFavoriteHeroEntityList: List<FavoriteHeroEntity>
    get() = map { it.toFavoriteHeroEntity }

val Hero.toFavoriteHeroEntity: FavoriteHeroEntity
    get() = FavoriteHeroEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = if (comicListAvailable) 1 else 0,
        insertDate = System.currentTimeMillis()
    )

val List<FavoriteHeroEntity>.toDomainCharacterList: List<Hero>
    get() = map { it.toDomainCharacter }

val FavoriteHeroEntity.toDomainCharacter: Hero
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

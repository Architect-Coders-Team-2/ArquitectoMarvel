package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Result
import com.architectcoders.domain.characters.Thumbnail

@Entity
data class FavoriteCharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val resourceURI: String?,
    val comicCollectionUri: String?,
    val comicListAvailable: Int?,
    val insertDate: Long?
)

val List<Result>.toFavoriteCharacterEntityList: List<FavoriteCharacterEntity>
    get() = map { it.toFavoriteCharacterEntity }

val Result.toFavoriteCharacterEntity: FavoriteCharacterEntity
    get() = FavoriteCharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        resourceURI = resourceURI,
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = if (comicListAvailable) 1 else 0,
        insertDate = System.currentTimeMillis()
    )

val List<FavoriteCharacterEntity>.toDomainCharacterList: List<Result>
    get() = map { it.toDomainCharacter }

val FavoriteCharacterEntity.toDomainCharacter: Result
    get() = Result(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail?.let {
            Thumbnail(
                it.substringBeforeLast("."),
                it.substringAfterLast(".")
            )
        },
        resourceURI = resourceURI,
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = comicListAvailable != null,
        pageNumber = null,
        insertDate = insertDate
    )

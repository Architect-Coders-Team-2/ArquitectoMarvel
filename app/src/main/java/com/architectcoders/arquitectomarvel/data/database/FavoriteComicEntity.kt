package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Thumbnail
import com.architectcoders.domain.comics.Result

@Entity
data class FavoriteComicEntity(
    @PrimaryKey
    val resourceUri: String,
    val thumbnail: String?,
    val title: String?,
    val insertDate: Long?
)

val List<Result>.toFavoriteComicEntityList: List<FavoriteComicEntity>
    get() = map { it.toFavoriteComicEntity }

val Result.toFavoriteComicEntity: FavoriteComicEntity
    get() = FavoriteComicEntity(
        resourceUri = resourceURI ?: "",
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        title = title,
        insertDate = System.currentTimeMillis()
    )

val List<FavoriteComicEntity>.toComicResultList: List<Result>
    get() = map { it.toComicResult }

val FavoriteComicEntity.toComicResult: Result
    get() = Result(
        null,
        null,
        null,
        null,
        resourceUri,
        thumbnail?.let {
            Thumbnail(
                it.substringBeforeLast("."),
                it.substringAfterLast(".")
            )
        },
        title,
        insertDate = insertDate
    )

package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Thumbnail
import com.architectcoders.domain.comics.Result

@Entity
data class ComicEntity(
    @PrimaryKey
    val resourceUri: String,
    val thumbnail: String?,
    val title: String?
)

val List<Result>.toComicEntityList: List<ComicEntity>
    get() = map { it.toComicEntity }

val Result.toComicEntity: ComicEntity
    get() = ComicEntity(
        resourceUri = resourceURI ?: "",
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        title = title
    )

val List<ComicEntity>.toComicResultList: List<Result>
    get() = map { it.toComicResult }

val ComicEntity.toComicResult: Result
    get() = Result(
        null,
        null,
        null,
        null,
        resourceUri,
        thumbnail?.split(".")?.let { Thumbnail(it[0], it[1]) },
        title
    )

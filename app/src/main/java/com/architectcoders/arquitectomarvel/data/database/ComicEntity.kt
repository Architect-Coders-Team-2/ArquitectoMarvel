package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.Thumbnail
import com.architectcoders.domain.comics.Comic

@Entity
data class ComicEntity(
    @PrimaryKey
    val resourceUri: String,
    val thumbnail: String?,
    val title: String?,
    val insertDate: Long?
)

val List<Comic>.toComicEntityList: List<ComicEntity>
    get() = map { it.toComicEntity }

val Comic.toComicEntity: ComicEntity
    get() = ComicEntity(
        resourceUri = resourceURI ?: "",
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        title = title,
        insertDate = System.currentTimeMillis()
    )

val List<ComicEntity>.toComicComicList: List<Comic>
    get() = map { it.toComicComic }

val ComicEntity.toComicComic: Comic
    get() = Comic(
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

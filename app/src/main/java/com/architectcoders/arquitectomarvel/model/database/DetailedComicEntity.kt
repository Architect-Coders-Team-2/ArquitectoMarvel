package com.architectcoders.arquitectomarvel.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.arquitectomarvel.model.comics.Result

@Entity
data class DetailedComicEntity(
    @PrimaryKey
    val resourceUri: String,
    val thumbnail: String?,
    val title: String?
)

val List<Result>.toDetailedComicEntityList: List<DetailedComicEntity>
    get() = map { it.toDetailedComicEntity }

val Result.toDetailedComicEntity: DetailedComicEntity
    get() = DetailedComicEntity(
        resourceUri = resourceURI ?: "",
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        title = title
    )

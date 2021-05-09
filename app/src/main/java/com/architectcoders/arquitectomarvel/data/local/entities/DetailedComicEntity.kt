package com.architectcoders.arquitectomarvel.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.Result
import com.architectcoders.module.domain.local_models.DetailedComic

@Entity
data class DetailedComicEntity(
    @PrimaryKey
    val resourceUri: String,
    val thumbnail: String,
    val title: String?
)

val List<Result>.toDetailedComicEntityList: List<DetailedComicEntity>
    get() = map { it.toDetailedComicEntity }

val Result.toDetailedComicEntity: DetailedComicEntity
    get() = DetailedComicEntity(
        resourceUri = resourceURI,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        title = title
    )

fun DetailedComicEntity.fromDetailedComicEntityToDetailedComic(): DetailedComic =
    DetailedComic(
        thumbnail = thumbnail,
        resourceUri = resourceUri,
        title = title
    )



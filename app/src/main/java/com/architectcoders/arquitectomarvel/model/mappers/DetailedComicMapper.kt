package com.architectcoders.arquitectomarvel.model.mappers

import com.architectcoders.arquitectomarvel.model.database.DetailedComicEntity
import com.architectcoders.module.domain.local_models.DetailedComic

fun DetailedComic.toDetailedComicEntity() : DetailedComicEntity {
    return DetailedComicEntity(
        resourceUri = resourceUri,
        thumbnail = thumbnail,
        title = title
    )
}
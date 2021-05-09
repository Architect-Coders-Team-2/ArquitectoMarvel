package com.architectcoders.arquitectomarvel.data.mappers

import com.architectcoders.arquitectomarvel.data.local.entities.DetailedComicEntity
import com.architectcoders.module.domain.local_models.DetailedComic

fun DetailedComic.toDetailedComicEntity() : DetailedComicEntity {
    return DetailedComicEntity(
        resourceUri = resourceUri,
        thumbnail = thumbnail?:"",
        title = title
    )
}
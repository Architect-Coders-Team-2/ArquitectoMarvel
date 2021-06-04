package com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters

import com.architectcoders.domain.Thumbnail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelThumbnail(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
)

val MarvelThumbnail.toLocalThumbnail: Thumbnail
    get() = Thumbnail(
        path, extension
    )

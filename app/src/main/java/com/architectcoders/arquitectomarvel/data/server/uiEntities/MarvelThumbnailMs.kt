package com.architectcoders.arquitectomarvel.data.server.uiEntities

import com.architectcoders.domain.Thumbnail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelThumbnailMs(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
)

val MarvelThumbnailMs.toLocalThumbnail: Thumbnail
    get() = Thumbnail(
        path, extension
    )

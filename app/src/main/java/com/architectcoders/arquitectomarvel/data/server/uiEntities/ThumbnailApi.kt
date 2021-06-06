package com.architectcoders.arquitectomarvel.data.server.uiEntities

import com.architectcoders.domain.Thumbnail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ThumbnailApi(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
)

val ThumbnailApi.toLocalThumbnail: Thumbnail
    get() = Thumbnail(
        path, extension
    )

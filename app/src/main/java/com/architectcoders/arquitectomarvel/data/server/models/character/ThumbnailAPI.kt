package com.architectcoders.arquitectomarvel.data.server.models.character

import com.architectcoders.domain.characters.Thumbnail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelThumbnail(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
)

val MarvelThumbnail.toDomainThumbnail: Thumbnail
    get() = Thumbnail(
        path, extension
    )

package com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelComics

import com.architectcoders.domain.Thumbnail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelImage(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
)

val List<MarvelImage>.toLocalListImage: List<Thumbnail>
    get() = map { it.toLocalImage }

val MarvelImage.toLocalImage: Thumbnail
    get() = Thumbnail(path, extension)

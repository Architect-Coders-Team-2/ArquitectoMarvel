package com.architectcoders.arquitectomarvel.data.server.uiEntities.comics

import com.architectcoders.domain.Thumbnail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicImageApi(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
)

val List<ComicImageApi>.toLocalListImage: List<Thumbnail>
    get() = map { it.toLocalImage }

val ComicImageApi.toLocalImage: Thumbnail
    get() = Thumbnail(path, extension)

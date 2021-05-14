package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import com.architectcoders.module.domain.models.ThumbnailDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Thumbnail(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
)

fun Thumbnail.toThumbnailDomain() : ThumbnailDomain =
    ThumbnailDomain(
        path,
        extension
    )

fun ThumbnailDomain.toToThumbailData(): Thumbnail =
    Thumbnail(path, extension)
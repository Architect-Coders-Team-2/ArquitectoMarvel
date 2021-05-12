package com.architectcoders.arquitectomarvel.data.server.models.comic

import com.architectcoders.domain.comics.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelImage(
    @Json(name = "extension")
    val extension: String?,
    @Json(name = "path")
    val path: String?
)

val List<MarvelImage>.toDomainListImage: List<Image>
    get() = map { it.toDomainImage }

val MarvelImage.toDomainImage: Image
    get() = Image(extension, path)

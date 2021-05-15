package com.architectcoders.arquitectomarvel.model.server.marvelComics

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

val List<MarvelImage>.toLocalListImage: List<Image>
    get() = map { it.toLocalImage }

val MarvelImage.toLocalImage: Image
    get() = Image(extension, path)
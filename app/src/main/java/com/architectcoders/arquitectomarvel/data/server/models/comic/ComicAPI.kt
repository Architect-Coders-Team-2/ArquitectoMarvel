package com.architectcoders.arquitectomarvel.data.server.models.comic

import com.architectcoders.domain.comics.Comic
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelComic(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "data")
    val comicData: MarvelData?
)

val MarvelComic.toDomainComic: Comic
    get() = Comic(code, comicData?.toDomainData)

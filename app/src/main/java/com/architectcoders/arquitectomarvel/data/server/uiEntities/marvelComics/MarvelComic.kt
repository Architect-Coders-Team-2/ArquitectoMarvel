package com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelComics

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

val MarvelComic.toLocalComic: Comic
    get() = Comic(code, comicData?.toLocalData)

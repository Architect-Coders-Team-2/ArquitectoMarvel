package com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelComics

import com.architectcoders.domain.comics.ComicsPayload
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelComic(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "data")
    val comicData: MarvelData?
)

val MarvelComic.toLocalComicsPayload: ComicsPayload
    get() = ComicsPayload(code, comicData?.toLocalDataHero)

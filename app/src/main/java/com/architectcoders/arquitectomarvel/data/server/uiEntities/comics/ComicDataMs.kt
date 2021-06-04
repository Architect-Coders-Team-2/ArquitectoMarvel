package com.architectcoders.arquitectomarvel.data.server.uiEntities.comics

import com.architectcoders.domain.comics.DataComics
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicDataMs(
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "count")
    val count: Int?,
    @Json(name = "results")
    val results: List<ComicMs>?
)

val ComicDataMs.toLocalDataHero: DataComics
    get() = DataComics(
        count, limit, offset, results?.toLocalListComic, total
    )

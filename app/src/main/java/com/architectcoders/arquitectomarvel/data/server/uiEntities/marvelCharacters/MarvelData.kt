package com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters

import com.architectcoders.domain.characters.Data
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelData(
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "count")
    val count: Int?,
    @Json(name = "results")
    val results: List<MarvelResult>?
)

val MarvelData.toLocalData: Data
    get() = Data(
        offset, limit, total, count, results?.toLocalListResult
    )

package com.architectcoders.arquitectomarvel.data.server.uiEntities.heros

import com.architectcoders.domain.heros.DataHeros
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HerosData(
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "count")
    val count: Int?,
    @Json(name = "results")
    val results: List<HerosMs>?
)

val HerosData.toLocalDataHeros: DataHeros
    get() = DataHeros(
        offset, limit, total, count, results?.toLocalListHero
    )

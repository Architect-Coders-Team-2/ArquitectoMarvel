package com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.architectcoders.module.domain.models.comics.DataComics as ComicsDataDomain

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "count")
    val count: Int?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "results")
    val results: List<Result>?,
    @Json(name = "total")
    val total: Int?
)

fun Data.toComicsDataDomain(): ComicsDataDomain =
    ComicsDataDomain(
        offset = offset,
        limit = limit,
        resultComics = results?.toListComicsResultDomain(),
        total = total,
        count = count
    )



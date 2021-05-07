package com.architectcoders.arquitectomarvel.model.comics

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

import com.architectcoders.module.domain.remote_models.Comics.Data as ComicsDataDomain

@Parcelize
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
) : Parcelable {

    fun toComicsDataDomain(): ComicsDataDomain =
        ComicsDataDomain(
            offset = offset,
            limit = limit,
            results = results?.toListComicsResultDomain(),
            total = total,
            count = count
        )
}



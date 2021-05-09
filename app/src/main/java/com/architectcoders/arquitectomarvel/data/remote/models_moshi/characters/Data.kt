package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

import com.architectcoders.module.domain.remote_models.Characters.Data as CharacterDataDomain

@Parcelize
@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "count")
    val count: Int?,
    @Json(name = "results")
    val results: List<Result>?
) : Parcelable

fun Data.toCharacterDataDomain(): CharacterDataDomain {
    return CharacterDataDomain(
        offset,
        limit,
        total,
        count,
        results?.toListCharacterResultDomain()
    )
}

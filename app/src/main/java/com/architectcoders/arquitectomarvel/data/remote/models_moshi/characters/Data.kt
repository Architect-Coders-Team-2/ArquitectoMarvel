package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.architectcoders.module.domain.models.characters.DataCharacters as CharacterDataDomain

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
)

fun Data.toCharacterDataDomain(): CharacterDataDomain {
    return CharacterDataDomain(
        offset,
        limit,
        total,
        count,
        results?.toListCharacterResultDomain()
    )
}

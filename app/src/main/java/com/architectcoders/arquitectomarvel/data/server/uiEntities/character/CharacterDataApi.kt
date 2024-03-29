package com.architectcoders.arquitectomarvel.data.server.uiEntities.character

import com.architectcoders.domain.character.CharacterData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDataApi(
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "count")
    val count: Int?,
    @Json(name = "results")
    val results: List<CharacterApi>?
)

val CharacterDataApi.toLocalCharacterData: CharacterData
    get() = CharacterData(
        offset, limit, total, count, results?.toLocalListCharacters
    )

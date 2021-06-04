package com.architectcoders.arquitectomarvel.data.server.uiEntities.heros

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HerosComicsMs(
    @Json(name = "available")
    val available: Int? = null,
    @Json(name = "collectionURI")
    val collectionURI: String = "",
    @Json(name = "returned")
    val returned: Int? = null
)

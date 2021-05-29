package com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics

import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Characters(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "collectionURI")
    val collectionURI: String?,
    @Json(name = "items")
    val items: List<Item>?,
    @Json(name = "returned")
    val returned: Int?
)

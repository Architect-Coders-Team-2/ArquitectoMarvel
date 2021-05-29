package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "name")
    val name: String?
)

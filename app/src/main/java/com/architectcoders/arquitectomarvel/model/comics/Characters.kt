package com.architectcoders.arquitectomarvel.model.comics

import android.os.Parcelable
import com.architectcoders.arquitectomarvel.model.characters.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable
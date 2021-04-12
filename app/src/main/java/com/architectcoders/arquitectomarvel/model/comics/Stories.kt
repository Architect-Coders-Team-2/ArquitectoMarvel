package com.architectcoders.arquitectomarvel.model.comics

import android.os.Parcelable
import com.architectcoders.arquitectomarvel.model.characters.ItemStories
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Stories(
    @Json(name = "available")
    val available: Int?,
    @Json(name = "collectionURI")
    val collectionURI: String?,
    @Json(name = "items")
    val items: List<ItemStories>?,
    @Json(name = "returned")
    val returned: Int?
) : Parcelable

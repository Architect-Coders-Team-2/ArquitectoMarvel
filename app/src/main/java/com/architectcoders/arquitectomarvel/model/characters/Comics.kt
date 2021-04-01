package com.architectcoders.arquitectomarvel.model.characters


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comics(
    @Json(name = "available")
    val available: Int? = null,
    @Json(name = "collectionURI")
    val collectionURI: String = "",
    @Json(name = "items")
    val items: List<Item> = emptyList(),
    @Json(name = "returned")
    val returned: Int? = null
) {

    override fun toString(): String {
        return "Comics(available=$available, collectionURI=$collectionURI, items=$items, returned=$returned)"
    }
}
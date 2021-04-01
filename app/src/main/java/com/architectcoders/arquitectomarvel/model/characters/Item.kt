package com.architectcoders.arquitectomarvel.model.characters


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "resourceURI")
    val resourceURI: String,
    @Json(name = "name")
    val name: String?
) {
    override fun toString(): String {
        return "Item(resourceURI=$resourceURI, name=$name)"
    }
}
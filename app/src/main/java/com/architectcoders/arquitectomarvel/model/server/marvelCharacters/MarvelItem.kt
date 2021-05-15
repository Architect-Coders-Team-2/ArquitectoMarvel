package com.architectcoders.arquitectomarvel.model.server.marvelCharacters

import com.architectcoders.domain.characters.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelItem(
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "name")
    val name: String?
)

val List<MarvelItem>.toLocalListItem: List<Item>
    get() = map { it.toLocalItem }

val MarvelItem.toLocalItem: Item
    get() = Item(
        resourceURI, name
    )
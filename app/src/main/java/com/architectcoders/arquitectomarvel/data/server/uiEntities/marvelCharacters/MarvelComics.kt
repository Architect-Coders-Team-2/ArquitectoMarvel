package com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters

import com.architectcoders.domain.characters.Comics
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelComics(
    @Json(name = "available")
    val available: Int? = null,
    @Json(name = "collectionURI")
    val collectionURI: String = "",
    @Json(name = "items")
    val items: List<MarvelItem> = emptyList(),
    @Json(name = "returned")
    val returned: Int? = null
)

val MarvelComics.toLocalComics: Comics
    get() = Comics(
        available, collectionURI, items.toLocalListItem, returned
    )

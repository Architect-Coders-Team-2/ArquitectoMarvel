package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.architectcoders.module.domain.models.Characters.CharactersComics as CharactersComicsDomain

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
)

fun Comics.toCharactersComicsDomain(): CharactersComicsDomain =
    CharactersComicsDomain(
        available,
        collectionURI,
        items,
        returned
    )
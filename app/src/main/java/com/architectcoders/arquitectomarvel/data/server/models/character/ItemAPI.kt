package com.architectcoders.arquitectomarvel.data.server.models.character

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

val List<MarvelItem>.toDomainListItem: List<Item>
    get() = map { it.toDomainItem }

val MarvelItem.toDomainItem: Item
    get() = Item(
        resourceURI, name
    )

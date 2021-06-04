package com.architectcoders.arquitectomarvel.data.server.uiEntities.heros

import com.architectcoders.arquitectomarvel.data.server.uiEntities.MarvelThumbnailMs
import com.architectcoders.arquitectomarvel.data.server.uiEntities.toLocalThumbnail
import com.architectcoders.domain.heros.Hero
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HerosMs(
    @Json(name = "id")
    val id: Int = -1,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "thumbnail")
    val thumbnailMs: MarvelThumbnailMs? = null,
    @Json(name = "resourceURI")
    val resourceURI: String? = null,
    @Json(name = "comics")
    val comicsMs: HerosComicsMs = HerosComicsMs()
)

val List<HerosMs>.toLocalListHero: List<Hero>
    get() = map { it.toLocalMarvelHero }

val HerosMs.toLocalMarvelHero: Hero
    get() = Hero(
        id,
        name,
        description,
        thumbnailMs?.toLocalThumbnail,
        comicsMs.collectionURI,
        comicsMs.available != null,
        null,
        null
    )

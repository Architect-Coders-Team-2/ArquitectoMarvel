package com.architectcoders.arquitectomarvel.data.server.models.character

import com.architectcoders.domain.characters.Result
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelResult(
    @Json(name = "id")
    val id: Int = -1,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "thumbnail")
    val thumbnail: MarvelThumbnail? = null,
    @Json(name = "resourceURI")
    val resourceURI: String? = null,
    @Json(name = "comics")
    val comics: MarvelComics = MarvelComics()
)

val List<MarvelResult>.toDomainlListResult: List<Result>
    get() = map { it.toDomainMarvelResult }

val MarvelResult.toDomainMarvelResult: Result
    get() = Result(
        id, name, description, thumbnail?.toDomainThumbnail, resourceURI, comics.toDomainComics
    )

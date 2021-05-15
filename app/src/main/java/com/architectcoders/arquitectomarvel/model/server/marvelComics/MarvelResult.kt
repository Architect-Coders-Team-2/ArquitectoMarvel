package com.architectcoders.arquitectomarvel.model.server.marvelComics

import com.architectcoders.arquitectomarvel.model.server.marvelCharacters.MarvelThumbnail
import com.architectcoders.arquitectomarvel.model.server.marvelCharacters.toLocalThumbnail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.architectcoders.domain.comics.Result

@JsonClass(generateAdapter = true)
data class MarvelResult(
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "images")
    val images: List<MarvelImage>?,
    @Json(name = "pageCount")
    val pageCount: Int?,
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "thumbnail")
    val thumbnail: MarvelThumbnail?,
    @Json(name = "title")
    val title: String?
)

val List<MarvelResult>.toLocalListResult: List<Result>
    get() = map { it.toLocalMarvelResult }

val MarvelResult.toLocalMarvelResult: Result
    get() = Result(
        description,
        id,
        images?.toLocalListImage,
        pageCount,
        resourceURI,
        thumbnail?.toLocalThumbnail,
        title
    )
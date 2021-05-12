package com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelComics

import com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters.MarvelThumbnail
import com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters.toLocalThumbnail
import com.architectcoders.domain.comics.Result
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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

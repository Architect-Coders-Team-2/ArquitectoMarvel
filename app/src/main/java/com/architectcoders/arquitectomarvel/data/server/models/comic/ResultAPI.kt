package com.architectcoders.arquitectomarvel.data.server.models.comic

import com.architectcoders.arquitectomarvel.data.server.models.character.MarvelThumbnail
import com.architectcoders.arquitectomarvel.data.server.models.character.toDomainThumbnail
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

val List<MarvelResult>.toDomainListResult: List<Result>
    get() = map { it.toDomainMarvelResult }

val MarvelResult.toDomainMarvelResult: Result
    get() = Result(
        description,
        id,
        images?.toDomainListImage,
        pageCount,
        resourceURI,
        thumbnail?.toDomainThumbnail,
        title
    )

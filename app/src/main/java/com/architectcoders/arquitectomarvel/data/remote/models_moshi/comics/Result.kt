package com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics

import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Thumbnail
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.toThumbnailDomain
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.toToThumbailData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.architectcoders.module.domain.models.Comics.ResultComics as ComicsResultDomain

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "resourceURI")
    val resourceURI: String,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail?,
    @Json(name = "title")
    val title: String?
) {

    fun toComicsResultDomain():ComicsResultDomain =
        ComicsResultDomain(
            title = title,
            resourceURI = resourceURI,
            thumbnailDomain = thumbnail?.toThumbnailDomain()
        )
}

fun List<Result>.toListComicsResultDomain(): List<ComicsResultDomain> =
    map { it.toComicsResultDomain() }

fun List<ComicsResultDomain>.fromListResult(): List<Result> =
    map { it.fromComicsResultDomain() }

fun ComicsResultDomain.fromComicsResultDomain(): Result =
    Result(
        resourceURI = resourceURI?:"",
        thumbnail = thumbnailDomain?.toToThumbailData(),
        title = title
    )




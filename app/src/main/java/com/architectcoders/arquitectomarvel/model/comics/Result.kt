package com.architectcoders.arquitectomarvel.model.comics

import android.os.Parcelable
import com.architectcoders.arquitectomarvel.model.characters.Thumbnail
import com.architectcoders.arquitectomarvel.model.characters.fromComicsThumbailDomain
import com.architectcoders.arquitectomarvel.model.characters.toComicsThumbailDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import com.architectcoders.module.domain.remote_models.Comics.Result as ComicsResultDomain

@Parcelize
@JsonClass(generateAdapter = true)
data class Result(
//    @Json(name = "characters")
//    val characters: Characters?,
//    @Json(name = "collectedIssues")
//    val collectedIssues: List<Item>?,
//    @Json(name = "collections")
//    val collections: List<Item>?,
//    @Json(name = "creators")
//    val creators: Creators?,
//    @Json(name = "dates")
//    val dates: List<Date>?,
//    @Json(name = "description")
//    val description: String?,
//    @Json(name = "diamondCode")
//    val diamondCode: String?,
//    @Json(name = "digitalId")
//    val digitalId: Int?,
//    @Json(name = "ean")
//    val ean: String?,
//    @Json(name = "events")
//    val events: Events?,
//    @Json(name = "format")
//    val format: String?,
//    @Json(name = "id")
//    val id: Int,
//    @Json(name = "images")
//    val images: List<Image>?,
//    @Json(name = "isbn")
//    val isbn: String?,
//    @Json(name = "issn")
//    val issn: String?,
//    @Json(name = "issueNumber")
//    val issueNumber: Int?,
//    @Json(name = "modified")
//    val modified: String?,
//    @Json(name = "pageCount")
//    val pageCount: Int?,
//    @Json(name = "prices")
//    val prices: List<Price>?,
    @Json(name = "resourceURI")
    val resourceURI: String,
//    @Json(name = "series")
//    val series: Series?,
//    @Json(name = "stories")
//    val stories: Stories?,
//    @Json(name = "textObjects")
//    val textObjects: List<TextObject>?,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail?,
    @Json(name = "title")
    val title: String?,
//    @Json(name = "upc")
//    val upc: String?,
//    @Json(name = "urls")
//    val urls: List<Url>?,
//    @Json(name = "variantDescription")
//    val variantDescription: String?,
//    @Json(name = "variants")
//    val variants: List<Variant>?
) : Parcelable {

    fun toComicsResultDomain():ComicsResultDomain =
        ComicsResultDomain(
//            id = id,
            title = title,
            resourceURI = resourceURI,
            thumbnail = thumbnail?.toComicsThumbailDomain()
        )
}

fun List<Result>.toListComicsResultDomain(): List<ComicsResultDomain> =
    map { it.toComicsResultDomain() }

fun List<ComicsResultDomain>.fromListResult(): List<Result> =
    map { it.fromComicsResultDomain() }

fun ComicsResultDomain.fromComicsResultDomain(): Result =
    Result(
//        id = id,
        resourceURI = resourceURI?:"",
        thumbnail = thumbnail?.fromComicsThumbailDomain(),
        title = title
    )




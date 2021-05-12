package com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics

import android.os.Parcelable
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Thumbnail
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.fromComicsThumbailDomain
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.toComicsThumbailDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import com.architectcoders.module.domain.remote_models.Comics.Result as ComicsResultDomain

@Parcelize
@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "resourceURI")
    val resourceURI: String,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail?,
    @Json(name = "title")
    val title: String?
) : Parcelable {

    fun toComicsResultDomain():ComicsResultDomain =
        ComicsResultDomain(
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
        resourceURI = resourceURI?:"",
        thumbnail = thumbnail?.fromComicsThumbailDomain(),
        title = title
    )




package com.architectcoders.arquitectomarvel.model.characters

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

import com.architectcoders.module.domain.remote_models.Characters.Result as CharactersResultDomain

@Parcelize
@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "id")
    val id: Int = -1,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "modified")
    val modified: String? = null,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail? = null,
    @Json(name = "resourceURI")
    val resourceURI: String? = null,
    @Json(name = "comics")
    val comics: Comics = Comics(),
    @Json(name = "series")
    val series: Series? = null,
    @Json(name = "stories")
    val stories: Stories? = null,
    @Json(name = "events")
    val events: Events? = null,
    @Json(name = "urls")
    val urls: List<Url>? = null
) : Parcelable

fun Result.toCharacterResultDomain(): CharactersResultDomain =
    CharactersResultDomain(
        id,
        name,
        description,
        modified,
        thumbnail?.toCharactersThumbailDomain(),
        resourceURI,
        comics.toCharactersComicsDomain()
    )

fun List<Result>.toListCharacterResultDomain() = map { it.toCharacterResultDomain() }


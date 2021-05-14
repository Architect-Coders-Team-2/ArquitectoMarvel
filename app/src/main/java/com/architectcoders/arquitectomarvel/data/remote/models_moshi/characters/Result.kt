package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.architectcoders.module.domain.models.Characters.ResultCharacters as CharactersResultDomain

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
    @Json(name = "urls")
    val urls: List<Url>? = null
)

fun Result.toCharacterResultDomain(): CharactersResultDomain =
    CharactersResultDomain(
        id,
        name,
        description,
        modified,
        thumbnail?.toThumbnailDomain(),
        resourceURI,
        comics.toCharactersComicsDomain()
    )

fun List<Result>.toListCharacterResultDomain() = map { it.toCharacterResultDomain() }


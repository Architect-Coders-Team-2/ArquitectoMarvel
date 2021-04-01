package com.architectcoders.arquitectomarvel.model.characters


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
)
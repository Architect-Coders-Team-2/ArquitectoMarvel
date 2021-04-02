package com.architectcoders.arquitectomarvel.model.characters


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "modified")
    val modified: String?,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail?,
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "comics")
    val comics: Comics?,
    @Json(name = "series")
    val series: Series?,
    @Json(name = "stories")
    val stories: Stories?,
    @Json(name = "events")
    val events: Events?,
    @Json(name = "urls")
    val urls: List<Url>?
) {

    // override for areItemsTheSame method run ok with equals operator '=='
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Result

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (modified != other.modified) return false
        if (thumbnail != other.thumbnail) return false
        if (resourceURI != other.resourceURI) return false
        if (comics != other.comics) return false
        if (series != other.series) return false
        if (stories != other.stories) return false
        if (events != other.events) return false
        if (urls != other.urls) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (modified?.hashCode() ?: 0)
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        result = 31 * result + (resourceURI?.hashCode() ?: 0)
        result = 31 * result + (comics?.hashCode() ?: 0)
        result = 31 * result + (series?.hashCode() ?: 0)
        result = 31 * result + (stories?.hashCode() ?: 0)
        result = 31 * result + (events?.hashCode() ?: 0)
        result = 31 * result + (urls?.hashCode() ?: 0)
        return result
    }
}
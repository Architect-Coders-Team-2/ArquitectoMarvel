package com.architectcoders.arquitectomarvel.data.server.uiEntities.comics

import com.architectcoders.arquitectomarvel.data.server.uiEntities.MarvelThumbnailMs
import com.architectcoders.arquitectomarvel.data.server.uiEntities.toLocalThumbnail
import com.architectcoders.domain.comics.Comic
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicMs(
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "images")
    val imageMs: List<ComicImageMs>?,
    @Json(name = "pageCount")
    val pageCount: Int?,
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "thumbnail")
    val thumbnailMs: MarvelThumbnailMs?,
    @Json(name = "title")
    val title: String?
)

val List<ComicMs>.toLocalListComic: List<Comic>
    get() = map { it.toLocalMarvelComic }

val ComicMs.toLocalMarvelComic: Comic
    get() = Comic(
        description,
        id,
        imageMs?.toLocalListImage,
        pageCount,
        resourceURI,
        thumbnailMs?.toLocalThumbnail,
        title,
        null
    )

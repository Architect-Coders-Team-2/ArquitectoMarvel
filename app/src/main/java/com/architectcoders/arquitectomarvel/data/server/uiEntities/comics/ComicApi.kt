package com.architectcoders.arquitectomarvel.data.server.uiEntities.comics

import com.architectcoders.arquitectomarvel.data.server.uiEntities.ThumbnailApi
import com.architectcoders.arquitectomarvel.data.server.uiEntities.toLocalThumbnail
import com.architectcoders.domain.comic.Comic
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicApi(
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "images")
    val comicImageApi: List<ComicImageApi>?,
    @Json(name = "pageCount")
    val pageCount: Int?,
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "thumbnail")
    val thumbnailApi: ThumbnailApi?,
    @Json(name = "title")
    val title: String?
)

val List<ComicApi>.toLocalListComic: List<Comic>
    get() = map { it.toLocalMarvelComic }

val ComicApi.toLocalMarvelComic: Comic
    get() = Comic(
        description,
        id,
        comicImageApi?.toLocalListImage,
        pageCount,
        resourceURI,
        thumbnailApi?.toLocalThumbnail,
        title,
        null
    )

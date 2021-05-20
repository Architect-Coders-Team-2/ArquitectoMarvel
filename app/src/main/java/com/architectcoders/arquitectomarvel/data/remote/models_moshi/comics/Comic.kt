package com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.architectcoders.module.domain.models.comics.Comics as ComicsDomain

@JsonClass(generateAdapter = true)
data class Comic(
    @Json(name = "attributionHTML")
    val attributionHTML: String?,
    @Json(name = "attributionText")
    val attributionText: String?,
    @Json(name = "code")
    val code: Int?,
    @Json(name = "copyright")
    val copyright: String?,
    @Json(name = "data")
    val comicData: Data?,
    @Json(name = "etag")
    val etag: String?,
    @Json(name = "status")
    val status: String?
)

fun Comic.toComicsDomain(): ComicsDomain =
    ComicsDomain(
        code = code,
        status = status,
        copyright = copyright,
        attributionText = attributionText,
        attributionHTML = attributionHTML,
        etag = etag,
        dataComics = comicData?.toComicsDataDomain()
    )

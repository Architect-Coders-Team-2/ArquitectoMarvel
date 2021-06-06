package com.architectcoders.arquitectomarvel.data.server.uiEntities.comics

import com.architectcoders.domain.comic.ComicsPayload
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicsPayloadApi(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "data")
    val comicsDataApi: ComicsDataApi?
)

val ComicsPayloadApi.toLocalComicsPayload: ComicsPayload
    get() = ComicsPayload(code, comicsDataApi?.toDomainDataComics)

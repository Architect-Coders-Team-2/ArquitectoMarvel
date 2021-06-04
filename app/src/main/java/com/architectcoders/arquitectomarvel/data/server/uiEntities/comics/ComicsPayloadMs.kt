package com.architectcoders.arquitectomarvel.data.server.uiEntities.comics

import com.architectcoders.domain.comics.ComicsPayload
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicsPayloadMs(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "data")
    val comicDataMs: ComicDataMs?
)

val ComicsPayloadMs.toLocalComicsPayload: ComicsPayload
    get() = ComicsPayload(code, comicDataMs?.toLocalDataHero)

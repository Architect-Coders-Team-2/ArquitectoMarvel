package com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters

import com.architectcoders.domain.heros.HerosPayload
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelCharacters(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "data")
    val characterData: MarvelData?
)

val MarvelCharacters.toLocalHerosPayload: HerosPayload
    get() = HerosPayload(code, status, characterData?.toLocalDataHeros)

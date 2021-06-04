package com.architectcoders.arquitectomarvel.data.server.uiEntities.heros

import com.architectcoders.domain.heros.HerosPayload
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HerosPayloadMs(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "data")
    val characterData: HerosData?
)

val HerosPayloadMs.toLocalHerosPayload: HerosPayload
    get() = HerosPayload(code, status, characterData?.toLocalDataHeros)

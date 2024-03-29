package com.architectcoders.arquitectomarvel.data.server.uiEntities.character

import com.architectcoders.domain.character.CharactersPayload
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterPayloadApi(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "data")
    val characterDataApi: CharacterDataApi?
)

val CharacterPayloadApi.toLocalCharactersPayload: CharactersPayload
    get() = CharactersPayload(code, status, characterDataApi?.toLocalCharacterData)

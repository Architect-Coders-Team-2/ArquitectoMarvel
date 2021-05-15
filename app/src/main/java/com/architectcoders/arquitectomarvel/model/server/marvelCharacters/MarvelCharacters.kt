package com.architectcoders.arquitectomarvel.model.server.marvelCharacters

import com.architectcoders.domain.characters.Characters
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

val MarvelCharacters.toLocalCharacters: Characters
    get() = Characters(code, status, characterData?.toLocalData)
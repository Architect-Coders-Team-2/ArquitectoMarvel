package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.architectcoders.module.domain.models.Characters.Characters as CharactersDomain

@JsonClass(generateAdapter = true)
data class Characters(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "copyright")
    val copyright: String?,
    @Json(name = "attributionText")
    val attributionText: String?,
    @Json(name = "attributionHTML")
    val attributionHTML: String?,
    @Json(name = "etag")
    val etag: String?,
    @Json(name = "data")
    val characterData: Data?
)

fun Characters.toCharactersDomain(): CharactersDomain {
    return CharactersDomain(
        code,
        status,
        copyright,
        attributionText,
        attributionHTML,
        etag,
        characterData?.toCharacterDataDomain()
    )
}

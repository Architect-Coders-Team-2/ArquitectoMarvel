package com.architectcoders.arquitectomarvel.model.characters

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable

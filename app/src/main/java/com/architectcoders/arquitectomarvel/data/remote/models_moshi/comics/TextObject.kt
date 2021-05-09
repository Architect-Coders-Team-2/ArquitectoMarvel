package com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TextObject(
    @Json(name = "language")
    val language: String?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "type")
    val type: String?
) : Parcelable

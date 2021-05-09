package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Url(
    @Json(name = "type")
    val type: String?,
    @Json(name = "url")
    val url: String?
) : Parcelable

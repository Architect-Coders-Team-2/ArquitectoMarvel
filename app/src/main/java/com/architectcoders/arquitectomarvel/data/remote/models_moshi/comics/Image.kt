package com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "extension")
    val extension: String?,
    @Json(name = "path")
    val path: String?
) : Parcelable

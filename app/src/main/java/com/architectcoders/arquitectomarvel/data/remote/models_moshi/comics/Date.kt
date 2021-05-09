package com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Date(
    @Json(name = "date")
    val date: String?,
    @Json(name = "type")
    val type: String?
) : Parcelable

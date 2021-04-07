package com.architectcoders.arquitectomarvel.model.comics

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CreatorsItem(
    @Json(name = "name")
    val name: String?,
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "role")
    val role: String?
) : Parcelable

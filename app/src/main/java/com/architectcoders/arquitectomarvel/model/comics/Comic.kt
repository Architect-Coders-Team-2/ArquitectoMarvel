package com.architectcoders.arquitectomarvel.model.comics

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Comic(
    @Json(name = "attributionHTML")
    val attributionHTML: String?,
    @Json(name = "attributionText")
    val attributionText: String?,
    @Json(name = "code")
    val code: Int?,
    @Json(name = "copyright")
    val copyright: String?,
    @Json(name = "data")
    val comicData: Data?,
    @Json(name = "etag")
    val etag: String?,
    @Json(name = "status")
    val status: String?
) : Parcelable

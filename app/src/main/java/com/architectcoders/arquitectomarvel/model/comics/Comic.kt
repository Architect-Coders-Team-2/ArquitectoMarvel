package com.architectcoders.arquitectomarvel.model.comics

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Comic(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "data")
    val comicData: Data?
) : Parcelable

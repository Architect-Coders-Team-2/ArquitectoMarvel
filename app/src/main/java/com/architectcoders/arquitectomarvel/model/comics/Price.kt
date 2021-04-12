package com.architectcoders.arquitectomarvel.model.comics

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Price(
    @Json(name = "price")
    val price: Double?,
    @Json(name = "type")
    val type: String?
) : Parcelable

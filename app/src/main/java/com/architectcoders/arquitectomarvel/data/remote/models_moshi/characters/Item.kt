package com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "resourceURI")
    val resourceURI: String?,
    @Json(name = "name")
    val name: String?
) : Parcelable {
    override fun toString(): String {
        return "Item(resourceURI=$resourceURI, name=$name)"
    }
}

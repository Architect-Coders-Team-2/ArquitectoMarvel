package com.architectcoders.arquitectomarvel.model.characters

import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("attributionHTML")
    val attributionHTML: String?,
    @SerializedName("attributionText")
    val attributionText: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("copyright")
    val copyright: String?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("status")
    val status: String?
) {
    override fun toString(): String {
        return "Characters(attributionHTML=$attributionHTML, attributionText=$attributionText, code=$code, copyright=$copyright, `data`=$`data`, etag=$etag, status=$status)"
    }
}
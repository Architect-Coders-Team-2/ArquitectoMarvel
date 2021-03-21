package com.architectcoders.arquitectomarvel.model.personajes


import com.google.gson.annotations.SerializedName

data class ItemXXX(
    @SerializedName("name")
    val name: String?,
    @SerializedName("resourceURI")
    val resourceURI: String?,
    @SerializedName("type")
    val type: String?
)
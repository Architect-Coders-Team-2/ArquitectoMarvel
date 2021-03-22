package com.architectcoders.arquitectomarvel.model.characters


import com.google.gson.annotations.SerializedName

data class ItemStories(
    @SerializedName("name")
    val name: String?,
    @SerializedName("resourceURI")
    val resourceURI: String?,
    @SerializedName("type")
    val type: String?
)
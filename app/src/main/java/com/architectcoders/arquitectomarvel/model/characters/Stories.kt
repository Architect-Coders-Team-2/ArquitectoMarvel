package com.architectcoders.arquitectomarvel.model.characters


import com.google.gson.annotations.SerializedName

data class Stories(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val itemsStories: List<ItemStories>?,
    @SerializedName("returned")
    val returned: Int?
)
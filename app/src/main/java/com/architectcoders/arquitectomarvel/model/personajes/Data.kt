package com.architectcoders.arquitectomarvel.model.personajes


import com.architectcoders.arquitectomarvel.model.personajes.Result
import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("total")
    val total: Int?
)
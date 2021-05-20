package com.architectcoders.domain.comics

import com.architectcoders.domain.characters.Thumbnail

data class Result(
    val description: String?,
    val id: Int?,
    val images: List<Image>?,
    val pageCount: Int?,
    val resourceURI: String?,
    val thumbnail: Thumbnail?,
    val title: String?,
    val insertDate: Long?
)

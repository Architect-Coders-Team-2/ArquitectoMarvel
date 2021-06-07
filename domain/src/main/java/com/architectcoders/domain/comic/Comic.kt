package com.architectcoders.domain.comic

import com.architectcoders.domain.Thumbnail

data class Comic(
    val description: String?,
    val id: Int?,
    val images: List<Thumbnail>?,
    val pageCount: Int?,
    val resourceURI: String?,
    val thumbnail: Thumbnail?,
    val title: String?,
    val insertDate: Long?
)

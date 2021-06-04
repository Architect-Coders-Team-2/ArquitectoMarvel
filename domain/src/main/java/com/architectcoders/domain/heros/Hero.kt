package com.architectcoders.domain.heros

import com.architectcoders.domain.Thumbnail

data class Hero(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: Thumbnail?,
    val comicCollectionUri: String?,
    val comicListAvailable: Boolean,
    var pageNumber: Int?,
    var insertDate: Long?
)

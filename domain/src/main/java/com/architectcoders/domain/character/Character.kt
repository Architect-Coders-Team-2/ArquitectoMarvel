package com.architectcoders.domain.character

import com.architectcoders.domain.Thumbnail

data class Character(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: Thumbnail?,
    val comicCollectionUri: String?,
    val comicListAvailable: Boolean,
    var pageNumber: Int?,
    var insertDate: Long?
)

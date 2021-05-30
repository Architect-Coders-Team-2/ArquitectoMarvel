package com.architectcoders.domain.characters

data class Result(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: Thumbnail?,
    val resourceURI: String?,
    val comicCollectionUri: String?,
    val comicListAvailable: Boolean,
    var pageNumber: Int?,
    var insertDate: Long?
)

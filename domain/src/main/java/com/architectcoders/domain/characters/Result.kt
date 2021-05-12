package com.architectcoders.domain.characters

data class Result(
    val id: Int = -1,
    val name: String? = null,
    val description: String? = null,
    val thumbnail: Thumbnail? = null,
    val resourceURI: String? = null,
    val comics: Comics = Comics()
)

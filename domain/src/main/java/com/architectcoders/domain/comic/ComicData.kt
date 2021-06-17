package com.architectcoders.domain.comic

data class ComicData(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val comics: List<Comic>?,
    val total: Int?
)

package com.architectcoders.domain.comic

data class DataComics(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val comics: List<Comic>?,
    val total: Int?
)

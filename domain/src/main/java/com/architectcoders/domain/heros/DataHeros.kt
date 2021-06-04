package com.architectcoders.domain.heros

data class DataHeros(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val heroes: List<Hero>?
)

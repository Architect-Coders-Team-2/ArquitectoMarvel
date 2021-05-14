package com.architectcoders.module.domain.models.Comics

data class DataComics(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val resultComics: List<ResultComics>?
)
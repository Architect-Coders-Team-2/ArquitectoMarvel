package com.architectcoders.module.domain.models.characters

data class DataCharacters(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val resultCharacters: List<ResultCharacters>?
)
package com.architectcoders.module.domain.models.characters

data class Characters(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val etag: String?,
    val charactersDataCharacters: DataCharacters?
)
package com.architectcoders.module.domain.models.characters

data class CharactersComics(
    val available: Int?,
    val collectionURI: String?,
    val items: List<Any>?,
    val returned: Int?
)
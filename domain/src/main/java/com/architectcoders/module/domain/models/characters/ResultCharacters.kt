package com.architectcoders.module.domain.models.characters

import com.architectcoders.module.domain.models.ThumbnailDomain

data class ResultCharacters(
    val id: Int,
    val name: String?,
    val description: String?,
    val modified: String?,
    val thumbnailDomain: ThumbnailDomain?,
    val resourceURI: String?,
    val charactersComics: CharactersComics?
)
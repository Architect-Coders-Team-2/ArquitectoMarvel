package com.architectcoders.domain.character

data class CharacterData(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val characters: List<Character>?
)

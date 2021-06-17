package com.architectcoders.domain.character

data class CharactersPayload(
    val code: Int?,
    val status: String?,
    val characterData: CharacterData?
)

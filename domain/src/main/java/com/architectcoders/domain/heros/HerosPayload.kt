package com.architectcoders.domain.heros

data class HerosPayload(
    val code: Int?,
    val status: String?,
    val characterDataHeros: DataHeros?
)

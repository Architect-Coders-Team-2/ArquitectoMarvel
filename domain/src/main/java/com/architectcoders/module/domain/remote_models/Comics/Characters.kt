package com.architectcoders.module.domain.remote_models.Comics

data class Characters(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ItemComics>?,
    val returned: Int?
)
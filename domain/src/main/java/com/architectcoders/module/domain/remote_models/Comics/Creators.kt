package com.architectcoders.module.domain.remote_models.Comics

data class Creators(
    val available: Int?,
    val collectionURI: String?,
    val items: List<Item>?,
    val returned: Int?
)
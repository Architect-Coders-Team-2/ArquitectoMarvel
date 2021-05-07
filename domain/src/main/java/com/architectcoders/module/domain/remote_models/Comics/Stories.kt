package com.architectcoders.module.domain.remote_models.Comics

data class Stories(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ItemXX>?,
    val returned: Int?
)
package com.architectcoders.module.domain.remote_models.Comics

data class Events(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ItemXXX>?,
    val returned: Int?
)
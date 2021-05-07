package com.architectcoders.module.domain.remote_models.Characters

data class Stories(
    val available: Int?,
    val collectionURI: String?,
    val items: List<Any>?,
    val returned: Int?
)
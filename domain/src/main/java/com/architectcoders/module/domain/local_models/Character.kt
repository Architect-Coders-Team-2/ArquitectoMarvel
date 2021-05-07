package com.architectcoders.module.domain.local_models

data class Character(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val comicCollectionUri: String,
    val comicListAvailable: Int?
)
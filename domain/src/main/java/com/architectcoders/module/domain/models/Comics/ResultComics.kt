package com.architectcoders.module.domain.models.Comics

import com.architectcoders.module.domain.models.ThumbnailDomain

data class ResultComics(
    val title: String?,
    val resourceURI: String?,
    val thumbnailDomain: ThumbnailDomain?
)
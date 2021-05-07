package com.architectcoders.module.domain.remote_models.Comics

data class Comics(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val etag: String?,
    val `data`: Data?
)
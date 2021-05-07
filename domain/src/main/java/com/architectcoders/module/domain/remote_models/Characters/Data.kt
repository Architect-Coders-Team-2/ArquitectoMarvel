package com.architectcoders.module.domain.remote_models.Characters

data class Data(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<Result>?
)
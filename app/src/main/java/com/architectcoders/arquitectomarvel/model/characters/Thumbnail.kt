package com.architectcoders.arquitectomarvel.model.characters

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

import com.architectcoders.module.domain.remote_models.Characters.Thumbnail as CharactersThumbailDomain

@Parcelize
@JsonClass(generateAdapter = true)
data class Thumbnail(
    @Json(name = "path")
    val path: String?,
    @Json(name = "extension")
    val extension: String?
) : Parcelable

fun Thumbnail.toThumbailDomain() : CharactersThumbailDomain =
    CharactersThumbailDomain(
        path,
        extension
    )
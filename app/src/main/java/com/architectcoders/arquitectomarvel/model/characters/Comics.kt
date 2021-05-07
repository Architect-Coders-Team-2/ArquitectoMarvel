package com.architectcoders.arquitectomarvel.model.characters

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

import com.architectcoders.module.domain.remote_models.Characters.Comics as CharactersComicsDomain

@Parcelize
@JsonClass(generateAdapter = true)
data class Comics(
    @Json(name = "available")
    val available: Int? = null,
    @Json(name = "collectionURI")
    val collectionURI: String = "",
    @Json(name = "items")
    val items: List<Item> = emptyList(),
    @Json(name = "returned")
    val returned: Int? = null
) : Parcelable {
    override fun toString(): String {
        return "Comics(available=$available, collectionURI=$collectionURI, items=$items, returned=$returned)"
    }
}

fun Comics.toCharactersComicsDomain(): CharactersComicsDomain =
    CharactersComicsDomain(
        available,
        collectionURI,
        items,
        returned
    )
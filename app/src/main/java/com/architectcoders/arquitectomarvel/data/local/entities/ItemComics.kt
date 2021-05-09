package com.architectcoders.arquitectomarvel.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Item

@Entity
data class ItemComics(
    @PrimaryKey(autoGenerate = false)
    val resourceURI: String,
    val name: String?,
    val collectionURI: String?
)

fun Item.dbItemComics(collectionURI: String): ItemComics {
    return ItemComics(
        resourceURI = resourceURI ?: "",
        name = name,
        collectionURI = collectionURI
    )
}

val ItemComics.toItem get() = Item(resourceURI, name)

val List<ItemComics>.toItems: List<Item>
    get() = map { it.toItem }

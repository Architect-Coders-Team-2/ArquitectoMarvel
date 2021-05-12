package com.architectcoders.domain.characters

data class Item(
    val resourceURI: String?,
    val name: String?
) {
    override fun toString(): String {
        return "Item(resourceURI=$resourceURI, name=$name)"
    }
}

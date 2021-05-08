package com.architectcoders.domain.characters

data class Comics(
    val available: Int? = null,
    val collectionURI: String = "",
    val items: List<Item> = emptyList(),
    val returned: Int? = null
) {
    override fun toString(): String {
        return "Comics(available=$available, collectionURI=$collectionURI, items=$items, returned=$returned)"
    }
}

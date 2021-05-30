package com.architectcoders.domain.comics

import com.architectcoders.domain.characters.Thumbnail

data class Result(
    val description: String?= null,
    val id: Int? = null,
    val images: List<Image>? = null,
    val pageCount: Int? = null,
    val resourceURI: String? = null,
    val thumbnail: Thumbnail? = null,
    val title: String? = null,
    val insertDate: Long? = null
) {
    companion object {
        val EMPTY_LIST_COMICS = listOf(
            Result(
                title = "No Comics for this Hero"
            )
        )
    }
}

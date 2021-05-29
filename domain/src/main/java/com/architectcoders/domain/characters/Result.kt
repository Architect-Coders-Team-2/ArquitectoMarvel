package com.architectcoders.domain.characters

data class Result(
    val id: Int = -1,
    val name: String? = null,
    val description: String? = null,
    val thumbnail: Thumbnail? = null,
    val resourceURI: String? = null,
    val comics: Comics = Comics()
) {

    // override for areItemsTheSame method run ok with equals operator '=='
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Result

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (thumbnail != other.thumbnail) return false
        if (resourceURI != other.resourceURI) return false
        if (comics != other.comics) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        result = 31 * result + (resourceURI?.hashCode() ?: 0)
        result = 31 * result + comics.hashCode()
        return result
    }
}

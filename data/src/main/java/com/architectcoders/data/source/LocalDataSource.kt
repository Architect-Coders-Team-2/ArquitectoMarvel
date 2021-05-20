package com.architectcoders.data.source

interface LocalDataSource {
    suspend fun getLocalCharacters(): Any?
    suspend fun insertFavoriteCharacter(vararg param: Any)
    suspend fun deleteFavoriteCharacter(vararg param: Any)
    suspend fun isCharacterFavorite(vararg param: Any): Any
    suspend fun insertFavoriteDetailedComic(vararg param: Any)
    suspend fun deleteFavoriteDetailedComic(vararg param: Any)
}

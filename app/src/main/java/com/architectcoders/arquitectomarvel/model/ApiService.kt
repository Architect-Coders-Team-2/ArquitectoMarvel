package com.architectcoders.arquitectomarvel.model

import com.architectcoders.arquitectomarvel.model.server.marvelCharacters.MarvelCharacters
import com.architectcoders.arquitectomarvel.model.server.marvelComics.MarvelComic
import com.architectcoders.domain.characters.Characters
import com.architectcoders.domain.comics.Comic
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 90
    ): MarvelCharacters

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): MarvelCharacters

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): MarvelComic?
}

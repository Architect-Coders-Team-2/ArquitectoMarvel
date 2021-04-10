package com.architectcoders.arquitectomarvel.model

import com.architectcoders.arquitectomarvel.model.characters.Characters
import com.architectcoders.arquitectomarvel.model.comics.Comic
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Characters

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Comic?
}

package com.architectcoders.arquitectomarvel.data.remote

import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Characters
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.Comic
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://gateway.marvel.com"
const val LIMIT = 90

interface ApiService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = LIMIT
    ): Characters

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Comic
}

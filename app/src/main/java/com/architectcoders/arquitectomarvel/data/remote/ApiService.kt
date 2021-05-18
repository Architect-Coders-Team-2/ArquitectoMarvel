package com.architectcoders.arquitectomarvel.data.remote

import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Characters
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.comics.Comic
import com.architectcoders.arquitectomarvel.ui.common.INCREMENT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://gateway.marvel.com"

interface ApiService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = INCREMENT
    ): Characters

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Comic
}

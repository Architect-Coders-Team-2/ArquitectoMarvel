package com.architectcoders.arquitectomarvel.model

import com.architectcoders.arquitectomarvel.model.characters.Characters
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Characters

}

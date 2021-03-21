package com.architectcoders.arquitectomarvel.model

import com.architectcoders.arquitectomarvel.model.personajes.Characters
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/public/characters")
    suspend fun listPersonajes(
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Characters

}

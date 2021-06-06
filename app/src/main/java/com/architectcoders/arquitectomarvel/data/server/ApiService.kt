package com.architectcoders.arquitectomarvel.data.server

import com.architectcoders.arquitectomarvel.data.server.uiEntities.character.CharacterPayload
import com.architectcoders.arquitectomarvel.data.server.uiEntities.comics.ComicsPayloadApi
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
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
        @Query("limit") limit: Int = REQUEST_LIMIT
    ): CharacterPayload

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): CharacterPayload

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): ComicsPayloadApi?
}

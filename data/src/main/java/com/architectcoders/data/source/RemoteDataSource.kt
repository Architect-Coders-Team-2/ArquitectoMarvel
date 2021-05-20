package com.architectcoders.data.source

interface RemoteDataSource {
    val credentialsDataSource: CredentialsDataSource
    suspend fun getRemoteCharacters(vararg param: Any): Any
    suspend fun getRemoteCharacterById(vararg param: Any): Any
    suspend fun getRemoteComics(vararg param: Any): Any?
}

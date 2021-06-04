package com.architectcoders.data.source

interface NetworkDataSource {
    suspend fun registerNetworkManager(listener: (Boolean) -> Unit)
    fun unregisterNetworkManager()
}

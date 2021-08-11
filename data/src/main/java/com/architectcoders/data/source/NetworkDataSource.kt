package com.architectcoders.data.source

interface NetworkDataSource {
    suspend fun handleNetworkManager(listener: (Boolean) -> Unit)
    fun unregisterNetworkCallback()
}

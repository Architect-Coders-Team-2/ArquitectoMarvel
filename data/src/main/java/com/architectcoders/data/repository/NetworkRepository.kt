package com.architectcoders.data.repository

import com.architectcoders.data.source.NetworkDataSource

class NetworkRepository(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun registerNetworkManager(listener: (Boolean) -> Unit) =
        networkDataSource.registerNetworkManager(listener)

    fun unregisterNetworkManager() =
        networkDataSource.unregisterNetworkManager()
}

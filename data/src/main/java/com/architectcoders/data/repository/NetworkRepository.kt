package com.architectcoders.data.repository

import com.architectcoders.data.source.NetworkDataSource

class NetworkRepository(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun handleNetworkManager(listener: (Boolean) -> Unit) =
        networkDataSource.handleNetworkManager(listener)

    fun unregisterNetworkCallback() = networkDataSource.unregisterNetworkCallback()
}

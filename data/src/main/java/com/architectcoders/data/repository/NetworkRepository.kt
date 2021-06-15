package com.architectcoders.data.repository

import com.architectcoders.data.source.NetworkDataSource

class NetworkRepository(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun manageNetworkManager(lifecycle: Any, listener: (Boolean) -> Unit) =
        networkDataSource.manageNetworkManager(lifecycle, listener)
}

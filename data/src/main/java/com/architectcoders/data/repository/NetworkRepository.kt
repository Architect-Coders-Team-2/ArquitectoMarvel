package com.architectcoders.data.repository

import com.architectcoders.data.source.NetworkDataSource
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun handleNetworkManager(listener: (Boolean) -> Unit) =
        networkDataSource.handleNetworkManager(listener)

    fun clearNetworks() = networkDataSource.clearNetworks()
}

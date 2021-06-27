package com.architectcoders.data.source

interface NetworkDataSource {
    suspend fun manageNetworkManager(lifecycle: Any, listener: (Boolean) -> Unit)
}

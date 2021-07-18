package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository

class HandleNetworkManager(private val networkRepository: NetworkRepository) {
    suspend fun invoke(lifecycle: Any, listener: (Boolean) -> Unit) =
        networkRepository.manageNetworkManager(lifecycle, listener)
}

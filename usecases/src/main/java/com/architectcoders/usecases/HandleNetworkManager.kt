package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository

class HandleNetworkManager(private val networkRepository: NetworkRepository) {
    suspend fun invoke(listener: (Boolean) -> Unit) =
        networkRepository.handleNetworkManager(listener)
}

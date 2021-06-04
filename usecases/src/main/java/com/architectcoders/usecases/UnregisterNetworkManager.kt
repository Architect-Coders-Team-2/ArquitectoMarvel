package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository

class UnregisterNetworkManager(private val networkRepository: NetworkRepository) {
    fun invoke() =
        networkRepository.unregisterNetworkManager()
}

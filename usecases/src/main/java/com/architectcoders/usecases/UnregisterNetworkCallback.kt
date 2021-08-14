package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository

class UnregisterNetworkCallback(private val networkRepository: NetworkRepository) {
    fun invoke() =
        networkRepository.unregisterNetworkCallback()
}

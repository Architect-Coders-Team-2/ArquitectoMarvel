package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository

class ClearNetworks(private val networkRepository: NetworkRepository) {
    fun invoke() =
        networkRepository.clearNetworks()
}

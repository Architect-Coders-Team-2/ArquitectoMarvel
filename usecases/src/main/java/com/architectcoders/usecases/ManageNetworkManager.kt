package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository

class ManageNetworkManager(private val networkRepository: NetworkRepository) {
    suspend fun invoke(lifecycle: Any, listener: (Boolean) -> Unit) =
        networkRepository.manageNetworkManager(lifecycle, listener)
}

package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository
import javax.inject.Inject

class HandleNetworkManager @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend fun invoke(listener: (Boolean) -> Unit) =
        networkRepository.handleNetworkManager(listener)
}

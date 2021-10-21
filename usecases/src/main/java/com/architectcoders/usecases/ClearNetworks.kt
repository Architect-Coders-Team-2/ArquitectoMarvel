package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository
import javax.inject.Inject

class ClearNetworks @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    fun invoke() =
        networkRepository.clearNetworks()
}

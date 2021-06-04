package com.architectcoders.usecases

import com.architectcoders.data.repository.NetworkRepository

class RegisterNetworkManager(private val networkRepository: NetworkRepository) :
    InvokeUseCase<(Boolean) -> Unit, Unit> {
    override suspend fun invoke(param: (Boolean) -> Unit) =
        networkRepository.registerNetworkManager(param)
}

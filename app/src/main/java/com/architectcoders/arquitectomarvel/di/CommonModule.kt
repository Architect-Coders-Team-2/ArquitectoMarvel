package com.architectcoders.arquitectomarvel.di

import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.usecases.HandleNetworkManager
import com.architectcoders.usecases.UnregisterNetworkCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class CommonModule {

    @Provides
    fun coroutineDispatcherProvider(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    fun handleNetworkManagerProvider(networkRepository: NetworkRepository): HandleNetworkManager =
        HandleNetworkManager(networkRepository)

    @Provides
    fun unregisterNetworkManagerProvider(networkRepository: NetworkRepository): UnregisterNetworkCallback =
        UnregisterNetworkCallback(networkRepository)
}

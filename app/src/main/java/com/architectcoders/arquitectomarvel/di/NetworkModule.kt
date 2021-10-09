package com.architectcoders.arquitectomarvel.di

import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.usecases.HandleNetworkManager
import com.architectcoders.usecases.ClearNetworks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

    @ViewModelScoped
    @Provides
    fun handleNetworkManagerProvider(networkRepository: NetworkRepository): HandleNetworkManager =
        HandleNetworkManager(networkRepository)

    @ViewModelScoped
    @Provides
    fun clearNetworksProvider(networkRepository: NetworkRepository): ClearNetworks =
        ClearNetworks(networkRepository)
}

package com.architectcoders.arquitectomarvel.ui.common

import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.usecases.HandleNetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CommonModule {

    @Provides
    fun handleNetworkManagerProvider(networkRepository: NetworkRepository): HandleNetworkManager =
        HandleNetworkManager(networkRepository)
}

package com.architectcoders.arquitectomarvel.di

import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.usecases.HandleNetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class CommonModule {

    @ViewModelScoped
    @Provides
    fun handleNetworkManagerProvider(networkRepository: NetworkRepository): HandleNetworkManager =
        HandleNetworkManager(networkRepository)
}

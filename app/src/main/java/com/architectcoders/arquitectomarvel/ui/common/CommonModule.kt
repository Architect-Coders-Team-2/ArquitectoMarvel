package com.architectcoders.arquitectomarvel.ui.common

import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.usecases.ManageNetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class CommonModule {

    @Provides
    fun manageNetworkManagerProvider(networkRepository: NetworkRepository): ManageNetworkManager =
        ManageNetworkManager(networkRepository)
}

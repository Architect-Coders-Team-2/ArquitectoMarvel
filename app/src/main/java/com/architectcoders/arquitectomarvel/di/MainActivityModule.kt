package com.architectcoders.arquitectomarvel.di

import com.architectcoders.arquitectomarvel.biometric.BiometricDataSourceImpl
import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.data.source.BiometricDataSource
import com.architectcoders.usecases.SetBiometricAuthentication
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class MainActivityModule {
    @ActivityScoped
    @Provides
    fun biometricRepositoryProvider(biometricDataSourceImpl: BiometricDataSourceImpl): BiometricRepository =
        BiometricRepository(biometricDataSourceImpl)

    @ActivityScoped
    @Provides
    fun setBiometricAuthenticationProvider(biometricRepository: BiometricRepository): SetBiometricAuthentication =
        SetBiometricAuthentication(biometricRepository)
}

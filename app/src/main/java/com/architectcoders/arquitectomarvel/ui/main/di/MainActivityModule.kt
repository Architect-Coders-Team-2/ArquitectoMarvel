package com.architectcoders.arquitectomarvel.ui.main.di

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
abstract class MainActivityModuleBinder {

    @ActivityScoped
    @Binds
    abstract fun bindsBiometricDataSource(biometricDataSourceImpl: BiometricDataSourceImpl): BiometricDataSource
}

@Module
@InstallIn(ActivityComponent::class)
class MainActivityModuleProvider {
    @ActivityScoped
    @Provides
    fun biometricRepositoryProvider(biometricDataSourceImpl: BiometricDataSourceImpl): BiometricRepository =
        BiometricRepository(biometricDataSourceImpl)

    @ActivityScoped
    @Provides
    fun setBiometricAuthenticationProvider(biometricRepository: BiometricRepository): SetBiometricAuthentication =
        SetBiometricAuthentication(biometricRepository)
}

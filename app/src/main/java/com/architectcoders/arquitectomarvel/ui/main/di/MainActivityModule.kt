package com.architectcoders.arquitectomarvel.ui.main.di

import android.content.Context
import com.architectcoders.arquitectomarvel.biometric.BiometricDataSourceImpl
import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.usecases.SetBiometricAuthentication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class MainActivityModule {

    @Provides
    fun biometricDataSourceProvider(@ActivityContext context: Context): BiometricDataSourceImpl =
        BiometricDataSourceImpl(context)

    @Provides
    fun biometricRepositoryProvider(biometricDataSourceImpl: BiometricDataSourceImpl): BiometricRepository =
        BiometricRepository(biometricDataSourceImpl)

    @Provides
    fun setBiometricAuthenticationProvider(biometricRepository: BiometricRepository): SetBiometricAuthentication =
        SetBiometricAuthentication(biometricRepository)
}

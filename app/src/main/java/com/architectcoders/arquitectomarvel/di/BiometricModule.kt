package com.architectcoders.arquitectomarvel.di

import android.app.Activity
import com.architectcoders.arquitectomarvel.biometric.BiometricDataSourceImpl
import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.data.source.BiometricDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object BiometricModule {

    @Provides
    fun provideBiometricDataSource(activity: Activity): BiometricDataSource =
        BiometricDataSourceImpl(activity)

    @Provides
    fun provideBiometricRepo(biometricDataSource: BiometricDataSource): BiometricRepository =
        BiometricRepository(biometricDataSource)
}
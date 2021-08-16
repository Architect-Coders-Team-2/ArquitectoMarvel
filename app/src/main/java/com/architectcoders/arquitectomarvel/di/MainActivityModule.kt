package com.architectcoders.arquitectomarvel.di

import android.content.Context
import android.content.SharedPreferences
import com.architectcoders.arquitectomarvel.biometric.AuthenticationStateDataSourceImpl
import com.architectcoders.arquitectomarvel.biometric.BiometricPromptDataSourceImpl
import com.architectcoders.arquitectomarvel.data.database.RoomDataSource
import com.architectcoders.arquitectomarvel.ui.common.BIOMETRIC_PREFERENCES
import com.architectcoders.data.repository.BiometricRepository
import com.architectcoders.data.source.AuthenticationStateDataSource
import com.architectcoders.usecases.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class MainActivityProvider {

    @ActivityScoped
    @Provides
    fun biometricSharedPreferencesProvider(@ActivityContext context: Context): SharedPreferences =
        context.getSharedPreferences(BIOMETRIC_PREFERENCES, Context.MODE_PRIVATE)

    @ActivityScoped
    @Provides
    fun biometricRepositoryProvider(
        authenticationStateDataSourceImpl: AuthenticationStateDataSourceImpl,
        biometricDataSourceImpl: BiometricPromptDataSourceImpl,
    ): BiometricRepository =
        BiometricRepository(authenticationStateDataSourceImpl, biometricDataSourceImpl)

    @ActivityScoped
    @Provides
    fun checkBiometricLoginValidityStateProvider(
        biometricRepository: BiometricRepository
    ): CheckAuthenticationState = CheckAuthenticationState(biometricRepository)

    @ActivityScoped
    @Provides
    fun checkIfUserIsAlreadyAuthenticatedProvider(
        biometricRepository: BiometricRepository
    ): CheckIfUserIsAlreadyAuthenticated = CheckIfUserIsAlreadyAuthenticated(biometricRepository)

    @ActivityScoped
    @Provides
    fun saveAuthenticationStateProvider(
        biometricRepository: BiometricRepository
    ): SaveAuthenticationState = SaveAuthenticationState(biometricRepository)

    @ActivityScoped
    @Provides
    fun canUserUseBiometricAuthenticationProvider(
        biometricRepository: BiometricRepository
    ): CanUserUseBiometricAuthentication = CanUserUseBiometricAuthentication(biometricRepository)

    @ActivityScoped
    @Provides
    fun setBiometricAuthenticationProvider(biometricRepository: BiometricRepository): SetBiometricAuthentication =
        SetBiometricAuthentication(biometricRepository)
}

@Module
@InstallIn(ActivityComponent::class)
abstract class MainActivityBinder {

    @ActivityScoped
    @Binds
    abstract fun bindsAuthenticationStateDataSource(
        authenticationStateDataSourceImpl: AuthenticationStateDataSourceImpl
    ): AuthenticationStateDataSource
}

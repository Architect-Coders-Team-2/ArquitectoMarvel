package com.architectcoders.arquitectomarvel.di

import com.architectcoders.arquitectomarvel.ui.common.CoroutineDispatchersImpl
import com.architectcoders.arquitectomarvel.ui.common.CoroutineDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DispatcherModule {

    @Binds
    abstract fun coroutineDispatcherBinder(
        coroutineDispatchersImpl: CoroutineDispatchersImpl
    ): CoroutineDispatchers
}

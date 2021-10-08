package com.architectcoders.arquitectomarvel.fakeModules

import com.architectcoders.arquitectomarvel.di.DispatcherModule
import com.architectcoders.arquitectomarvel.ui.common.CoroutineDispatchers
import com.architectcoders.arquitectomarvel.utils.CoroutineDispatchersTestImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [DispatcherModule::class]
)
abstract class FakeDispatcherModule {

    @ExperimentalCoroutinesApi
    @Binds
    abstract fun coroutineDispatcherBinder(
        coroutineDispatchersTestImpl: CoroutineDispatchersTestImpl
    ): CoroutineDispatchers
}

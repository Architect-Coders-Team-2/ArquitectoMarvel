package com.architectcoders.arquitectomarvel.fakeModules

import com.architectcoders.arquitectomarvel.di.DataModuleBinderForRetrofit
import com.architectcoders.arquitectomarvel.di.DataModuleBinderForRoom
import com.architectcoders.arquitectomarvel.fakeDataSources.FakeLocalDataSource
import com.architectcoders.arquitectomarvel.fakeDataSources.FakeRemoteDataSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModuleBinderForRoom::class]
)
object RemovesDataModuleBinderForRoom

@Module
@InstallIn(SingletonComponent::class)
abstract class FakeDataModuleBinderForRoom {

    @Singleton
    @Binds
    abstract fun bindsFakeLocalDataSource(fakeLocalDataSource: FakeLocalDataSource): LocalDataSource
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModuleBinderForRetrofit::class]
)
object RemovesDataModuleBinderForRetrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class FakeDataModuleBinderForRetrofit {

    @Singleton
    @Binds
    abstract fun bindsFakeRemoteDataSource(fakeRemoteDataSource: FakeRemoteDataSource): RemoteDataSource
}

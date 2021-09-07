package com.architectcoders.arquitectomarvel.fakeModules

import com.architectcoders.arquitectomarvel.di.DataModuleBinder
import com.architectcoders.arquitectomarvel.di.DataModuleProvider
import com.architectcoders.arquitectomarvel.fakeDataSources.FakeCredentialsDataSource
import com.architectcoders.arquitectomarvel.fakeDataSources.FakeLocalDataSource
import com.architectcoders.arquitectomarvel.fakeDataSources.FakeNetworkDataSource
import com.architectcoders.arquitectomarvel.fakeDataSources.FakeRemoteDataSource
import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.data.repository.NetworkRepository
import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.NetworkDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModuleBinder::class]
)
abstract class FakeDataModuleBinder {
    @Singleton
    @Binds
    abstract fun bindsFakeLocationDataSource(fakeLocalDataSource: FakeLocalDataSource): LocalDataSource

    @Singleton
    @Binds
    abstract fun bindsFakeCredentialsDatSrc(fakeCredentialsDataSource: FakeCredentialsDataSource): CredentialsDataSource

    @Singleton
    @Binds
    abstract fun bindsFakeRemoteDataSource(fakeRemoteDataSource: FakeRemoteDataSource): RemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsFakeNetworkDataSource(fakeNetworkDataSource: FakeNetworkDataSource): NetworkDataSource
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModuleProvider::class]
)
class FakeDataModuleProvider {

    @Singleton
    @Provides
    fun fakeMarvelRepositoryProvider(
        fakeRemoteDataSource: FakeRemoteDataSource,
        fakeLocalDataSource: FakeLocalDataSource
    ): MarvelRepository = MarvelRepository(fakeRemoteDataSource, fakeLocalDataSource)

    @Singleton
    @Provides
    fun fakeNetworkRepositoryProvider(fakeNetworkDataSource: FakeNetworkDataSource): NetworkRepository =
        NetworkRepository(fakeNetworkDataSource)
}

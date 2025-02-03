package br.com.badbit.droidchat.data.network.di

import br.com.badbit.droidchat.data.network.NetworkDataSource
import br.com.badbit.droidchat.data.network.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    @Singleton
    fun bindsNetworkDataSource(networkDataSource: NetworkDataSourceImpl): NetworkDataSource

}
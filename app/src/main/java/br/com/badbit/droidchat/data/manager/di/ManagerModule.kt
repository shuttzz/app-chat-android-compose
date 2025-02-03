package br.com.badbit.droidchat.data.manager.di

import br.com.badbit.droidchat.data.manager.selfuser.SelfUserManager
import br.com.badbit.droidchat.data.manager.selfuser.SelfUserManagerImpl
import br.com.badbit.droidchat.data.manager.token.TokenManager
import br.com.badbit.droidchat.data.manager.token.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ManagerModule {

    @Binds
    @Singleton
    fun bindTokenManager(tokenManager: TokenManagerImpl): TokenManager

    @Binds
    @Singleton
    fun bindSelfUserManager(selfUserManager: SelfUserManagerImpl): SelfUserManager

}
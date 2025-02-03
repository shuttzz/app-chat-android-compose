package br.com.badbit.droidchat.data.manager.token

import android.content.Context
import br.com.badbit.droidchat.data.datastore.TokensKeys
import br.com.badbit.droidchat.data.di.IoDispatcher
import br.com.badbit.droidchat.data.manager.CryptoManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SecureTokenManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TokenManager {
    override val accessToken: Flow<String>
        get() = flowOf(CryptoManager.decryptData(context, TokensKeys.ACCESS_TOKEN.name))

    override suspend fun saveAccessToken(token: String) {
        withContext(ioDispatcher) {
            CryptoManager.encryptData(context, TokensKeys.ACCESS_TOKEN.name, token)
        }
    }

    override suspend fun clearAccessToken() {
        withContext(ioDispatcher) {
            CryptoManager.encryptData(context, TokensKeys.ACCESS_TOKEN.name, "")
        }
    }
}
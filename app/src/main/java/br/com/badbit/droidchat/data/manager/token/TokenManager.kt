package br.com.badbit.droidchat.data.manager.token

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    val accessToken: Flow<String>

    suspend fun saveAccessToken(token: String)

    suspend fun clearAccessToken()
}
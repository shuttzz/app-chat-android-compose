package br.com.badbit.droidchat.data.network

import br.com.badbit.droidchat.data.network.model.AuthRequest
import br.com.badbit.droidchat.data.network.model.CreateAccountRequest
import br.com.badbit.droidchat.data.network.model.ImageResponse
import br.com.badbit.droidchat.data.network.model.TokenResponse

interface NetworkDataSource {

    suspend fun signUp(request: CreateAccountRequest)

    suspend fun signIn(request: AuthRequest): TokenResponse

    suspend fun uploadProfilePicture(filePath: String): ImageResponse

}
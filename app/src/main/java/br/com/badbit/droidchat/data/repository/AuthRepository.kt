package br.com.badbit.droidchat.data.repository

import br.com.badbit.droidchat.model.CreateAccount
import br.com.badbit.droidchat.model.Image

interface AuthRepository {

    suspend fun getAccessToken(): String?

    suspend fun clearAccessToken()

    suspend fun signUp(createAccount: CreateAccount): Result<Unit>

    suspend fun signIn(username: String, password: String): Result<Unit>

    suspend fun uploadProfilePicture(filePath: String): Result<Image>

    suspend fun authenticate(token: String): Result<Unit>

}
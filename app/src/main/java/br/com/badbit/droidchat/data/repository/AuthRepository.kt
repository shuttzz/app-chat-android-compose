package br.com.badbit.droidchat.data.repository

import br.com.badbit.droidchat.model.CreateAccount

interface AuthRepository {

    suspend fun signUp(createAccount: CreateAccount)

    suspend fun signIn(username: String, password: String)

}
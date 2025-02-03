package br.com.badbit.droidchat.data.manager.selfuser

import br.com.badbit.droidchat.SelfUser
import kotlinx.coroutines.flow.Flow

interface SelfUserManager {

    val selfUserFlow: Flow<SelfUser>

    suspend fun saveSelfUserData(
        firstName: String,
        lastName: String,
        profilePictureUrl: String,
        username: String
    )

    suspend fun clearSelfUser()
}
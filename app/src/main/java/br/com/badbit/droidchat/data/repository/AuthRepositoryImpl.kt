package br.com.badbit.droidchat.data.repository

import br.com.badbit.droidchat.data.di.IoDispatcher
import br.com.badbit.droidchat.data.manager.selfuser.SelfUserManager
import br.com.badbit.droidchat.data.manager.token.TokenManager
import br.com.badbit.droidchat.data.network.NetworkDataSource
import br.com.badbit.droidchat.data.network.model.AuthRequest
import br.com.badbit.droidchat.data.network.model.CreateAccountRequest
import br.com.badbit.droidchat.model.CreateAccount
import br.com.badbit.droidchat.model.Image
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val tokenManager: TokenManager,
    private val selfUserManager: SelfUserManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun getAccessToken(): String? {
        return tokenManager.accessToken.firstOrNull()
    }

    override suspend fun clearAccessToken() {
        withContext(ioDispatcher) {
            tokenManager.clearAccessToken()
        }
    }

    override suspend fun signUp(createAccount: CreateAccount): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
                networkDataSource.signUp(
                    request = CreateAccountRequest(
                        username = createAccount.username,
                        password = createAccount.password,
                        firstName = createAccount.firstName,
                        lastName = createAccount.lastName,
                        profilePictureId = createAccount.profilePictureId
                    )
                )
            }
        }
    }

    override suspend fun signIn(username: String, password: String): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
                val tokenResponse = networkDataSource.signIn(
                    request = AuthRequest(
                        username = username,
                        password = password
                    )
                )

                tokenManager.saveAccessToken(tokenResponse.token)
            }
        }
    }

    override suspend fun uploadProfilePicture(filePath: String): Result<Image> {
        return withContext(ioDispatcher) {
            runCatching {
                val imageResponse = networkDataSource.uploadProfilePicture(filePath)

                Image(
                    id = imageResponse.id,
                    name = imageResponse.name,
                    type = imageResponse.type,
                    url = imageResponse.url
                )
            }
        }
    }

    override suspend fun authenticate(token: String): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
                val userReponse = networkDataSource.authenticate(token)

                selfUserManager.saveSelfUserData(
                    firstName = userReponse.firstName,
                    lastName = userReponse.lastName,
                    profilePictureUrl = userReponse.profilePictureUrl ?: "",
                    username = userReponse.username
                )
            }
        }
    }
}
package br.com.badbit.droidchat.data.network

import br.com.badbit.droidchat.data.network.model.AuthRequest
import br.com.badbit.droidchat.data.network.model.CreateAccountRequest
import br.com.badbit.droidchat.data.network.model.ImageResponse
import br.com.badbit.droidchat.data.network.model.TokenResponse
import br.com.badbit.droidchat.data.network.model.UserReponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
) : NetworkDataSource {
    override suspend fun signUp(request: CreateAccountRequest) {
        httpClient.post("signup") {
            setBody(request)
        }.body<Unit>()
    }

    override suspend fun signIn(request: AuthRequest): TokenResponse {
        return httpClient.post("signin") {
            setBody(request)
        }.body()
    }

    override suspend fun uploadProfilePicture(filePath: String): ImageResponse {
        val file = File(filePath)
        return httpClient.submitFormWithBinaryData(
            url = "profile-picture",
            formData = formData {
                append("image", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, "image/${file.extension}")
                    append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                })
            }
        ).body()
    }

    override suspend fun authenticate(token: String): UserReponse {
        return httpClient.get("authenticate") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }
}
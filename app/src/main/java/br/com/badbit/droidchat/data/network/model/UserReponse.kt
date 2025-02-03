package br.com.badbit.droidchat.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserReponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String?,
    val username: String
)

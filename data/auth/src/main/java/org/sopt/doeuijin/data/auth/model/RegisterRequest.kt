package org.sopt.doeuijin.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("username")
    val id: String? = null,
    @SerialName("password")
    val password: String? = null,
    @SerialName("nickname")
    val nickname: String? = null,
)
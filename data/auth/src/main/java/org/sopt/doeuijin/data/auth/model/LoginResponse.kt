package org.sopt.doeuijin.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("username")
    val userId: String = "",
    @SerialName("nickname")
    val nickname: String = "",
)
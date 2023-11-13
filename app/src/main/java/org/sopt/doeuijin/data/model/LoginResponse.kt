package org.sopt.doeuijin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("nickname")
    val nickname: String? = null,
    @SerialName("username")
    val username: String? = null,
)
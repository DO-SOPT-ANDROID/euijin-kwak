package org.sopt.doeuijin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("nickname")
    val id: String? = null,
    @SerialName("password")
    val password: String? = null,
    @SerialName("username")
    val username: String? = null,
)
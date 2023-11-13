package org.sopt.doeuijin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("username")
    val id: String? = null,
    @SerialName("password")
    val password: String? = null,
)
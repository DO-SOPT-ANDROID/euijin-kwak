package org.sopt.doeuijin.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberInfoResponse(
    @SerialName("nickname")
    val nickname: String? = null,
    @SerialName("username")
    val username: String? = null,
)
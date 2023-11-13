package org.sopt.doeuijin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameResponse(
    @SerialName("isExist")
    val isExist: Boolean? = null,
)
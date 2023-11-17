package org.sopt.doeuijin.feature.home.profile

import kotlinx.serialization.Serializable

sealed class Profile {
    abstract val name: String
    abstract val description: String
    abstract val image: String

    data class MyProfile(
        override val name: String = "",
        override val description: String = "",
        override val image: String = "",
    ) : Profile()

    @Serializable
    data class FriendProfile(
        override val name: String = "",
        override val description: String = "",
        override val image: String = "",
    ) : Profile()
}
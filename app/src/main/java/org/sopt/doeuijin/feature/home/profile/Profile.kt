package org.sopt.doeuijin.feature.home.profile

sealed class Profile {
    abstract val name: String
    abstract val description: String
    abstract val image: String

    data class MyProfile(
        override val name: String,
        override val description: String,
        override val image: String,
    ) : Profile()

    data class FriendProfile(
        override val name: String,
        override val description: String,
        override val image: String,
    ) : Profile()
}
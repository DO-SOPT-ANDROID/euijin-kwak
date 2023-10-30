package org.sopt.doeuijin.feature.home.profile

sealed class Profile {
    data class MyProfile(
        val name: String,
        val description: String,
        val image: String,
    ) : Profile()

    data class FriendProfile(
        val name: String,
        val description: String,
        val image: String,
    ) : Profile()
}
package org.sopt.doeuijin.feature.main

import org.sopt.doeuijin.feature.home.profile.Profile

class MainContract {
    data class MainState(
        val id: String = "",
        val pw: String = "",
        val nickName: String = "",
        val userList: List<Profile> = emptyList(),
    )

    sealed class MainSideEffect {
        object ShowToast : MainSideEffect()
        object MoveToTopPage : MainSideEffect()
    }
}
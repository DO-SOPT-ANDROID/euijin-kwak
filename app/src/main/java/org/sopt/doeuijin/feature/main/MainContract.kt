package org.sopt.doeuijin.feature.main

class MainContract {
    data class MainState(
        val id: String = "",
        val pw: String = "",
        val nickName: String = "",
    )

    sealed class MainSideEffect {
        object ShowToast : MainSideEffect()
    }
}
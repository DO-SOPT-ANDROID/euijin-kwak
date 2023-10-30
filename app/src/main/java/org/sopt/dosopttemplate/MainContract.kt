package org.sopt.dosopttemplate

class MainContract {
    data class MainState(
        val id: String = "",
        val registerId: String = "",
        val pw: String = "",
        val registerPw: String = "",
        val nickname: String = "",
    )

    sealed interface MainSideEffect {
        data class ShowToast(val message: String) : MainSideEffect
        object LoginSuccess : MainSideEffect
        object RegistrationSuccess : MainSideEffect
    }
}

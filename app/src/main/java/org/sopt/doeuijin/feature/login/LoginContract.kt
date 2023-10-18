package org.sopt.doeuijin.feature.login

class LoginContract {
    sealed interface Effect {
        data class LoginSuccess(
            val id: String,
            val pw: String,
            val nickName: String,
        ) : Effect
        object LoginFailed : Effect
        object SignUp : Effect
        object IdIncorrect : Effect
        object PasswordIncorrect : Effect
    }

    data class UiState(
        val id: String = "",
        val pw: String = "",
        val nickName: String = "",
        val isAutoLoginEnabled: Boolean = false,
    )
}

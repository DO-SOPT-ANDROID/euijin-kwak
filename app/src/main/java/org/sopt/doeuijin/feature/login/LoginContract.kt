package org.sopt.doeuijin.feature.login

class LoginContract {
    sealed interface Effect {
        data class LoginSuccess(
            val id: String,
            val nickName: String,
        ) : Effect

        object LoginFailed : Effect
        object IdIncorrect : Effect
        object PasswordIncorrect : Effect
        object InputFieldsEmpty : Effect
    }

    data class UiState(
        val inputId: String = "",
        val inputPw: String = "",
        val isAutoLoginEnabled: Boolean = false,
    )
}

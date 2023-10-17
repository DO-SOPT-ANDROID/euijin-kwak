package org.sopt.doeuijin.feature.login

class LoginContract {
    sealed interface Effect {
        data class Home(
            val id: String,
            val pw: String,
            val nickName: String,
        ) : Effect

        object SignUp : Effect
        data class SnackBar(val message: String) : Effect
    }

    data class UiState(
        val id: String = "",
        val pw: String = "",
        val nickName: String = "",
        val isAutoLoginEnabled: Boolean = false,
    )
}

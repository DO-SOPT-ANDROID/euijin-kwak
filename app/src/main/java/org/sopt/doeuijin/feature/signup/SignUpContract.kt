package org.sopt.doeuijin.feature.signup

import androidx.annotation.StringRes

class SignUpContract {
    sealed interface Effect {
        object Login : Effect
        data class Error(
            val errorType: SignUpError,
            @StringRes val messageRes: Int,
        ) : Effect

        data class ShowToast(
            @StringRes val messageRes: Int,
        ) : Effect
    }

    data class UiState(
        val id: String = "",
        val pw: String = "",
        val nickName: String = "",
    )
}

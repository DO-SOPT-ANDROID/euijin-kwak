package org.sopt.doeuijin.feature.signup

import android.text.Editable
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.common.extension.isValidPattern
import org.sopt.doeuijin.R
import org.sopt.doeuijin.data.auth.model.RegisterRequest
import org.sopt.doeuijin.data.auth.repository.DefaultAuthRepository

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
    val isIdValid: Boolean = false,
    val isPwValid: Boolean = false,
    val isNickNameValid: Boolean = false,
)

class SignUpViewModel : ViewModel() {

    companion object {
        val idPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}$".toRegex()
        val pwPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,12}$".toRegex()
    }

    private val authRepository = DefaultAuthRepository()

    private val _event = MutableSharedFlow<Effect>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    fun updateId(id: Editable?) {
        _state.value = state.value.copy(id = id.toString())
        validateField(state.value.id, SignUpError.ID, R.string.signup_id_error)
    }

    fun updatePw(pw: Editable?) {
        _state.value = state.value.copy(pw = pw.toString())
        validateField(state.value.pw, SignUpError.PW, R.string.signup_pw_error)
    }

    fun updateNickName(nickName: Editable?) {
        _state.value = state.value.copy(nickName = nickName.toString())
        validateField(state.value.nickName, SignUpError.NICK_NAME, R.string.signup_nickname_error)
    }

    fun signUp() {
        viewModelScope.launch {
            val id = state.value.id
            val pw = state.value.pw
            val nickName = state.value.nickName
            saveUserIdentifier(id, pw, nickName)
        }
    }

    private fun saveUserIdentifier(id: String, pw: String, nickName: String) {
        viewModelScope.launch {
            val registerRequest = RegisterRequest(id, pw, nickName)
            runCatching {
                authRepository.register(registerRequest)
            }.onSuccess {
                _event.emit(Effect.Login)
            }.onFailure {
                Log.e("SignUpViewModel", "signUp: ", it)
                _event.emit(
                    Effect.ShowToast(
                        messageRes = R.string.signup_failed,
                    ),
                )
            }
        }
    }

    private fun validateField(
        value: String,
        errorType: SignUpError,
        errorMessageRes: Int,
    ) {
        val isValid = when (errorType) {
            SignUpError.ID -> value.isValidPattern(idPattern)
            SignUpError.PW -> value.isValidPattern(pwPattern)
            SignUpError.NICK_NAME -> value.isNotEmpty()
        }

        if (!isValid) postError(errorType, errorMessageRes)
        updateFieldValidationStatus(errorType, isValid)
    }

    private fun updateFieldValidationStatus(errorType: SignUpError, isValid: Boolean) {
        val updatedState = when (errorType) {
            SignUpError.ID -> state.value.copy(isIdValid = isValid)
            SignUpError.PW -> state.value.copy(isPwValid = isValid)
            SignUpError.NICK_NAME -> state.value.copy(isNickNameValid = isValid)
        }
        _state.value = updatedState
    }

    private fun postError(errorType: SignUpError, messageRes: Int) {
        viewModelScope.launch {
            _event.emit(
                Effect.Error(
                    errorType = errorType,
                    messageRes = messageRes,
                ),
            )
        }
    }
}
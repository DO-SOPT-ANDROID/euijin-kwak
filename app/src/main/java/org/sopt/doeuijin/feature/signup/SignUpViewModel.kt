package org.sopt.doeuijin.feature.signup

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.common.extension.isNotValidLength
import org.sopt.doeuijin.R
import org.sopt.doeuijin.data.auth.model.RegisterRequest
import org.sopt.doeuijin.data.auth.repository.DefaultAuthRepository

class SignUpViewModel : ViewModel() {

    private val authRepository = DefaultAuthRepository()

    private val _event = MutableSharedFlow<SignUpContract.Effect>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(SignUpContract.UiState())
    val state = _state.asStateFlow()

    fun updateId(id: Editable?) {
        _state.value = state.value.copy(id = id.toString())
    }

    fun updatePw(pw: Editable?) {
        _state.value = state.value.copy(pw = pw.toString())
    }

    fun updateNickName(nickName: Editable?) {
        _state.value = state.value.copy(nickName = nickName.toString())
    }

    fun signUp() {
        viewModelScope.launch {
            val id = state.value.id
            val pw = state.value.pw
            val nickName = state.value.nickName

            when {
                id.isNotValidLength(ID_MIN_LENGTH, ID_MAX_LENGTH) -> {
                    _event.emit(
                        SignUpContract.Effect.Error(
                            errorType = SignUpError.ID,
                            messageRes = R.string.signup_id_error,
                        ),
                    )
                }

                pw.isNotValidLength(PW_MIN_LENGTH, PW_MAX_LENGTH) -> {
                    _event.emit(
                        SignUpContract.Effect.Error(
                            errorType = SignUpError.PW,
                            messageRes = R.string.signup_pw_error,
                        ),
                    )
                }

                nickName.isEmpty() -> {
                    _event.emit(
                        SignUpContract.Effect.Error(
                            errorType = SignUpError.NICK_NAME,
                            messageRes = R.string.signup_nickname_error,
                        ),
                    )
                }

                else -> {
                    saveUserIdentifier(id, pw, nickName)
                }
            }
        }
    }

    private fun saveUserIdentifier(id: String, pw: String, nickName: String) {
        viewModelScope.launch {
            val registerRequest = RegisterRequest(id, pw, nickName)
            runCatching {
                authRepository.register(registerRequest)
            }.onSuccess {
                _event.emit(SignUpContract.Effect.Login)
            }.onFailure {
                Log.e("SignUpViewModel", "signUp: ", it)
                _event.emit(
                    SignUpContract.Effect.ShowToast(
                        messageRes = R.string.signup_failed,
                    ),
                )
            }
        }
    }

    companion object {
        const val ID_MIN_LENGTH = 6
        const val ID_MAX_LENGTH = 10
        const val PW_MIN_LENGTH = 8
        const val PW_MAX_LENGTH = 12
    }
}
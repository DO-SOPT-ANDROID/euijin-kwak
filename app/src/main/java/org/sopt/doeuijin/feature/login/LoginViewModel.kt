package org.sopt.doeuijin.feature.login

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.doeuijin.data.DefaultAuthRepository

class LoginViewModel : ViewModel() {

    private val defaultAuthRepository = DefaultAuthRepository()

    private val _event = MutableSharedFlow<LoginContract.Effect>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(LoginContract.UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fetchRegisterUserIdentifierToState()
        }
    }

    fun handleLoginButtonClick(isAutoLogin: Boolean) {
        viewModelScope.launch {
            if (state.value.inputId.isEmpty() || state.value.inputPw.isEmpty()) {
                _event.emit(LoginContract.Effect.InputFieldsEmpty)
                return@launch
            }
            fetchRegisterUserIdentifierToState()
            login(isAutoLogin)
        }
    }

    private suspend fun login(isAutoLogin: Boolean) {
        state.value.run {
            checkLoginValidity(inputId, registerId, inputPw, registerPw, nickName, isAutoLogin)
        }
    }

    private suspend fun checkLoginValidity(
        inputId: String,
        registerId: String,
        inputPw: String,
        registerPw: String,
        nickname: String,
        isAutoLogin: Boolean,
    ) {
        when {
            inputId != registerId -> {
                _event.emit(LoginContract.Effect.IdIncorrect)
            }

            inputPw != registerPw -> {
                _event.emit(LoginContract.Effect.PasswordIncorrect)
            }

            else -> {
                setAutoLogin(isAutoLogin)
                _event.emit(LoginContract.Effect.LoginSuccess(registerId, registerPw, nickname))
            }
        }
    }

    fun updateId(id: Editable?) {
        _state.value = state.value.copy(inputId = id.toString())
    }

    fun updatePw(pw: Editable?) {
        _state.value = state.value.copy(inputPw = pw.toString())
    }

    private fun setAutoLogin(
        isAutoLogin: Boolean,
    ) {
        viewModelScope.launch {
            defaultAuthRepository.setAutoLogin(isAutoLogin)
        }
    }

    private suspend fun fetchRegisterUserIdentifierToState() {
        val (id, pw, nickName) = defaultAuthRepository.getUserIdentifier()
        _state.value = state.value.copy(
            registerId = id,
            registerPw = pw,
            nickName = nickName,
            isAutoLoginEnabled = defaultAuthRepository.checkAutoLogin(),
        )
    }
}
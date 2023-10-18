package org.sopt.doeuijin.feature.login

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.doeuijin.data.DefaultUserRepository

class LoginViewModel : ViewModel() {

    // 생성자 주입은 DI쓰고나서 할게요... 아... 힐트쓸걸...
    private val defaultUserRepository = DefaultUserRepository()

    private val _event = MutableSharedFlow<LoginContract.Effect>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(LoginContract.UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val (id, pw, nickName) = defaultUserRepository.getUserIdentifier()
            _state.value = LoginContract.UiState(
                registerId = id,
                registerPw = pw,
                nickName = nickName,
                isAutoLoginEnabled = defaultUserRepository.checkAutoLogin(),
            )
            Log.d("LoginViewModel", "id: $id, pw: $pw, nickName: $nickName")
        }
    }

    fun handleLoginButtonClick(isAutoLogin: Boolean) {
        viewModelScope.launch {
            if (state.value.inputId.isEmpty() || state.value.inputPw.isEmpty()) {
                _event.emit(LoginContract.Effect.InputFieldsEmpty)
                return@launch
            }
            login(isAutoLogin)
        }
    }

    fun login(isAutoLogin: Boolean) {
        viewModelScope.launch {
            state.value.run {
                checkLoginValidity(inputId, registerId, inputPw, registerPw, nickName, isAutoLogin)
            }
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
            defaultUserRepository.setAutoLogin(isAutoLogin)
        }
    }
}
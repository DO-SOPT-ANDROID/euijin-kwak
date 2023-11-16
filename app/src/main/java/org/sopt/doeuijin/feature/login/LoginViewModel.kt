package org.sopt.doeuijin.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.doeuijin.data.DefaultUserRepository
import org.sopt.doeuijin.data.auth.model.LoginRequest
import org.sopt.doeuijin.data.auth.repository.DefaultAuthRepository

class LoginViewModel : ViewModel() {

    private val userRepository = DefaultUserRepository()
    private val authRepository = DefaultAuthRepository()

    private val _event = MutableSharedFlow<LoginContract.Effect>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(LoginContract.UiState())
    val state = _state.asStateFlow()

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            if (!userRepository.checkAutoLogin()) return@launch
            val userInfo = userRepository.getUserIdentifier()
            updateId(userInfo.first)
            updatePw(userInfo.second)
            login(isAutoLogin = true)
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

    private suspend fun login(isAutoLogin: Boolean) {
        val loginRequest = LoginRequest(
            id = state.value.inputId,
            password = state.value.inputPw,
        )
        runCatching {
            authRepository.login(loginRequest)
        }.onSuccess {
            setAutoLogin(
                id = state.value.inputId,
                pw = state.value.inputPw,
                userName = it.nickname,
                isAutoLogin = isAutoLogin,
            )
            _event.emit(LoginContract.Effect.LoginSuccess(it.userId, it.nickname))
        }.onFailure {
            if (isAutoLogin) {
                _event.emit(LoginContract.Effect.LoginFailed)
            }
        }
    }

    fun updateId(id: String?) {
        _state.value = state.value.copy(inputId = id.toString())
    }

    fun updatePw(pw: String?) {
        _state.value = state.value.copy(inputPw = pw.toString())
    }

    private fun setAutoLogin(id: String, pw: String, userName: String, isAutoLogin: Boolean) {
        viewModelScope.launch {
            userRepository.setAutoLogin(
                id = id,
                pw = pw,
                userName = userName,
                isAutoLogin = isAutoLogin,
            )
        }
    }
}
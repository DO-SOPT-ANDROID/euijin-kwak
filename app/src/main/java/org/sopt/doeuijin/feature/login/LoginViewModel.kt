package org.sopt.doeuijin.feature.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.doeuijin.data.DefaultUserRepository
import org.sopt.doeuijin.data.auth.model.LoginRequest
import org.sopt.doeuijin.data.auth.model.LoginResponse
import org.sopt.doeuijin.data.auth.repository.DefaultAuthRepository

class LoginViewModel : ViewModel() {

    private val userRepository = DefaultUserRepository()
    private val authRepository = DefaultAuthRepository()

    private val _effect = MutableSharedFlow<LoginContract.Effect>()
    val effect = _effect.asSharedFlow()
    private val _state = MutableStateFlow(LoginContract.UiState())
    val state = _state.asStateFlow()

    init {
        checkAutoLogin()
    }

    fun handleLoginButtonClick(isAutoLogin: Boolean) {
        viewModelScope.launch {
            if (state.value.inputId.isEmpty() || state.value.inputPw.isEmpty()) {
                _effect.emit(LoginContract.Effect.InputFieldsEmpty)
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
            if (isAutoLogin) {
                autoLogin(isAutoLogin = true, loginResponse = it)
            }
            _effect.emit(LoginContract.Effect.LoginSuccess(it.userId, it.nickname))
        }.onFailure {
            autoLogin(isAutoLogin = false)
            _effect.emit(LoginContract.Effect.LoginFailed)
            Log.e("LoginViewModel", "login: ${it.message}")
        }
    }

    fun updateId(id: String?) {
        _state.value = state.value.copy(inputId = id.toString())
    }

    fun updatePw(pw: String?) {
        _state.value = state.value.copy(inputPw = pw.toString())
    }

    private fun autoLogin(
        isAutoLogin: Boolean,
        loginResponse: LoginResponse? = null,
    ) {
        userRepository.setAutoLogin(isAutoLogin = isAutoLogin)
        if (loginResponse != null) {
            userRepository.setUserIdentifier(
                id = state.value.inputId,
                pw = state.value.inputPw,
                loginResponse.nickname,
            )
        }
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
}
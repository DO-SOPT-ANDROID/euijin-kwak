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

    private val defaultUserRepository = DefaultUserRepository()

    private val _event = MutableSharedFlow<LoginContract.Effect>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(LoginContract.UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val (id, pw, nickName) = defaultUserRepository.getUserIdentifier()
            _state.value = LoginContract.UiState(
                id = id,
                pw = pw,
                nickName = nickName,
                isAutoLoginEnabled = defaultUserRepository.checkAutoLogin(),
            )
            Log.d("LoginViewModel", "id: $id, pw: $pw, nickName: $nickName")
        }
    }

    fun login(isAutoLogin: Boolean) {
        viewModelScope.launch {
            val id = state.value.id
            val pw = state.value.pw
            val nickName = state.value.nickName

            val (registerId, registerPw, registerNickName) = defaultUserRepository.getUserIdentifier()
            when {
                id != registerId -> {
                    _event.emit(LoginContract.Effect.IdIncorrect)
                }

                pw != registerPw -> {
                    _event.emit(LoginContract.Effect.PasswordIncorrect)
                }

                else -> {
                    setAutoLogin(isAutoLogin)
                    _event.emit(LoginContract.Effect.LoginSuccess(id, pw, nickName))
                }
            }
        }
    }

    fun moveToSignUp() {
        viewModelScope.launch {
            _event.emit(LoginContract.Effect.SignUp)
        }
    }

    fun updateId(id: Editable?) {
        _state.value = state.value.copy(id = id.toString())
    }

    fun updatePw(pw: Editable?) {
        _state.value = state.value.copy(pw = pw.toString())
    }

    private fun setAutoLogin(
        isAutoLogin: Boolean,
    ) {
        viewModelScope.launch {
            defaultUserRepository.setAutoLogin(isAutoLogin)
        }
    }
}
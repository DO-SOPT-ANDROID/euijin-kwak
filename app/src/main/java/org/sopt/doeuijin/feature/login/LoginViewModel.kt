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

    fun setAutoLogin(
        isAutoLogin: Boolean,
    ) {
        viewModelScope.launch {
            defaultUserRepository.setAutoLogin(isAutoLogin)
            val (id, pw, nickName) = defaultUserRepository.getUserIdentifier()
            _event.emit(LoginContract.Effect.Home(id, pw, nickName))
        }
    }
}
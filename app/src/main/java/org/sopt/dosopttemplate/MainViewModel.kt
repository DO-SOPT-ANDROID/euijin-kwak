package org.sopt.dosopttemplate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(MainContract.MainState())
    val state get() = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainContract.MainSideEffect>()
    val event get() = _event.asSharedFlow()

    private fun emitToast(message: String) {
        viewModelScope.launch { _event.emit(MainContract.MainSideEffect.ShowToast(message)) }
    }

    fun updateState(newState: MainContract.MainState) {
        _state.value = newState
    }

    fun login() {
        val state = state.value
        when {
            state.id.isEmpty() || state.id != state.registerId -> emitToast("아이디를 확인해주세요.")
            state.pw.isEmpty() || state.pw != state.registerPw -> emitToast("비밀번호를 확인해주세요.")
            else -> viewModelScope.launch {
                _event.emit(MainContract.MainSideEffect.LoginSuccess)
            }
        }
    }

    fun register() {
        val id = state.value.registerId
        val pw = state.value.registerPw
        val nickname = state.value.nickname

        when {
            id.length !in 6..10 -> emitToast("ID는 6~10 글자로 입력해주세요.")
            pw.length !in 8..12 -> emitToast("비밀번호는 8~12 글자로 입력해주세요.")
            nickname.isBlank() -> emitToast("닉네임을 입력해주세요.")
            else -> completeRegistration(id, pw, nickname)
        }
    }

    fun clearRegisterInfo() {
        updateState(state.value.copy(registerId = "", registerPw = "", nickname = ""))
    }

    private fun completeRegistration(id: String, pw: String, nickname: String) {
        viewModelScope.launch {
            updateState(state.value.copy(id = id, pw = pw, nickname = nickname))
            _event.emit(MainContract.MainSideEffect.RegistrationSuccess)
        }
    }
}

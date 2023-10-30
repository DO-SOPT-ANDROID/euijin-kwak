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

    fun updateState(newState: MainContract.MainState) {
        _state.value = newState
    }

    fun login() {
        viewModelScope.launch {
            val state = state.value
            if (state.id.isEmpty() || state.id != state.registerId) {
                _event.emit(MainContract.MainSideEffect.ShowToast("아이디를 확인해주세요."))
                return@launch
            }
            if (state.pw.isEmpty() || state.pw != state.registerPw) {
                _event.emit(MainContract.MainSideEffect.ShowToast("비밀번호를 확인해주세요."))
                return@launch
            }
            _event.emit(MainContract.MainSideEffect.LoginSuccess)
        }
    }
}

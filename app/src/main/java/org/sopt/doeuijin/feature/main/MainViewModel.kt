package org.sopt.doeuijin.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.doeuijin.data.DefaultUserRepository
import org.sopt.doeuijin.feature.home.profile.Profile

class MainViewModel : ViewModel() {

    private val userRepository: DefaultUserRepository = DefaultUserRepository()

    private val _state = MutableStateFlow(MainContract.MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainContract.MainSideEffect>()
    val event = _event.asSharedFlow()

    init {
        getUserList()
    }

    fun getUserList() {
        val userList = userRepository.getUserList()
        updateState(state.value.copy(userList = userList))
    }

    fun addFriend(friendProfile: Profile.FriendProfile) {
        userRepository.addUser(friendProfile)
        getUserList()
    }

    fun removeFriend(friendProfile: Profile.FriendProfile) {
        userRepository.removeUser(friendProfile)
        getUserList()
    }

    fun onEvent(mainEvent: MainContract.MainSideEffect) {
        viewModelScope.launch {
            _event.emit(mainEvent)
        }
    }

    fun updateState(mainState: MainContract.MainState) {
        _state.value = mainState
    }
}
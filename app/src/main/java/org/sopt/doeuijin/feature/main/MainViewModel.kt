package org.sopt.doeuijin.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.doeuijin.data.user.model.ResponseUserListDto
import org.sopt.doeuijin.data.user.repository.DefaultReqresRepository
import org.sopt.doeuijin.feature.home.profile.Profile

class MainViewModel : ViewModel() {

    private val reqresRepository = DefaultReqresRepository()

    private val _state = MutableStateFlow(MainContract.MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainContract.MainSideEffect>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            getReqresUser()
        }
    }

    private suspend fun getReqresUser() {
        runCatching {
            reqresRepository.getUsers(2)
        }.onSuccess {
            updateState(
                state.value.copy(profileList = it.data.toProfile()),
            )
        }.onFailure {
            Log.e("MainViewModel", "getReqresUser: $it")
        }
    }

    fun updateState(mainState: MainContract.MainState) {
        _state.value = mainState
    }

    private fun List<ResponseUserListDto.ResponseReqresUserDto?>?.toProfile(): List<Profile> {
        return this?.map {
            Profile.FriendProfile(
                name = "${it?.first_name} ${it?.last_name}",
                description = it?.email.orEmpty(),
                image = it?.avatar.orEmpty(),
            )
        } ?: emptyList()
    }
}
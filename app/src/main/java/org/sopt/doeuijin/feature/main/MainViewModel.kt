package org.sopt.doeuijin.feature.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.doeuijin.feature.home.profile.Profile

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainContract.MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainContract.MainSideEffect>()
    val event = _event.asSharedFlow()

    init {
        updateState(
            state.value.copy(
                profileList = listOf(
                    Profile.MyProfile(
                        name = "곽의진",
                        description = "ios를 타파하자",
                        image = "SOPT",
                    ),
                    Profile.FriendProfile(
                        name = "이삭",
                        description = "이삭 토스트 대표님",
                        image = "SOPT",
                    ),
                    Profile.FriendProfile(
                        name = "박강희",
                        description = "등산 오세요 여러분~",
                        image = "SOPT",
                    ),
                    Profile.FriendProfile(
                        name = "이태희",
                        description = "종무식 빨리 왔으면 좋겠다",
                        image = "SOPT",
                    ),
                    Profile.FriendProfile(
                        name = "우상욱",
                        description = "축구팀 모집하고 있습니다, 많관부",
                        image = "SOPT",
                    ),
                    Profile.FriendProfile(
                        name = "김상호",
                        description = "술은 전통주가 최고지",
                        image = "SOPT",
                    ),
                ),
            ),
        )
    }

    fun updateState(mainState: MainContract.MainState) {
        _state.value = mainState
    }
}
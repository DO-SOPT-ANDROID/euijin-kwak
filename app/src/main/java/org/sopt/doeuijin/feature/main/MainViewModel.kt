package org.sopt.doeuijin.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
                        image = "",
                    ),
                    Profile.FriendProfile(
                        name = "이삭",
                        description = "이삭 토스트 대표님",
                        image = "https://cdn-dantats.stunning.kr/prod/portfolios/52a78f74-d616-4525-bad1-641b9314a273/covers/order_sub_2196784_1_190403125806.jpg.small?q=50&t=crop&e=0x0&s=600x600",
                    ),
                    Profile.FriendProfile(
                        name = "조관희",
                        description = "이삭 토스트 대표님",
                        image = "https://cdn-dantats.stunning.kr/prod/portfolios/52a78f74-d616-4525-bad1-641b9314a273/covers/order_sub_2196784_1_190403125806.jpg.small?q=50&t=crop&e=0x0&s=600x600",
                    ),
                    Profile.FriendProfile(
                        name = "이연진",
                        description = "이삭 토스트 대표님",
                        image = "https://cdn-dantats.stunning.kr/prod/portfolios/52a78f74-d616-4525-bad1-641b9314a273/covers/order_sub_2196784_1_190403125806.jpg.small?q=50&t=crop&e=0x0&s=600x600",
                    ),
                    Profile.FriendProfile(
                        name = "김민정",
                        description = "이삭 토스트 대표님",
                        image = "https://cdn-dantats.stunning.kr/prod/portfolios/52a78f74-d616-4525-bad1-641b9314a273/covers/order_sub_2196784_1_190403125806.jpg.small?q=50&t=crop&e=0x0&s=600x600",
                    ),
                    Profile.FriendProfile(
                        name = "박강희",
                        description = "등산 오세요 여러분~",
                        image = "",
                    ),
                    Profile.FriendProfile(
                        name = "이태희",
                        description = "종무식 빨리 왔으면 좋겠다",
                        image = "",
                    ),
                    Profile.FriendProfile(
                        name = "우상욱",
                        description = "축구팀 모집하고 있습니다, 많관부",
                        image = "https://i.namu.wiki/i/UifEv197Swv_tuhQI7M2LI9sdGzVdlSt65n-OJf9yKccpFinxPb0T-c_eHFQSCEi2iICW2dQSodfASyil90X-g.webp",
                    ),
                    Profile.FriendProfile(
                        name = "김상호",
                        description = "술은 전통주가 최고지",
                        image = "https://i.namu.wiki/i/56fi6OsGyyU9mox5T21_aCz5BIGar2dAGhVLsETD7TrnI7ErfxDNSpe0eEZMg1Ypa0ioFqUTRt3gb3lfMAzEtA.webp",
                    ),
                ),
            ),
        )
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
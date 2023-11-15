package org.sopt.doeuijin.data

import androidx.core.content.edit
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.sopt.common.key.SharedPreferenceKey
import org.sopt.doeuijin.container.sharedPreferences
import org.sopt.doeuijin.feature.home.profile.Profile

class DefaultUserRepository {

    init {
        val userList = getUserList()
        if (userList.isEmpty()) {
            setUserList(
                listOf(
                    Profile.FriendProfile(
                        name = "이삭",
                        description = "이삭 토스트 대표님",
                        image = "https://cdn-dantats.stunning.kr/prod/portfolios/52a78f74-d616-4525-bad1-641b9314a273/covers/order_sub_2196784_1_190403125806.jpg.small?q=50&t=crop&e=0x0&s=600x600",
                    ),
                    Profile.FriendProfile(
                        name = "조관희",
                        description = "헬스장 사장님",
                        image = "",
                    ),
                    Profile.FriendProfile(
                        name = "이연진",
                        description = "미래 백만 유튜버",
                        image = "",
                    ),
                    Profile.FriendProfile(
                        name = "김민정",
                        description = "33기 갓기",
                        image = "",
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
            )
        }
    }

    fun getUserList(): List<Profile.FriendProfile> {
        return try {
            val userList = sharedPreferences.getString(SharedPreferenceKey.USER_LIST, null).toString()
            Json.decodeFromString(userList)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun setUserList(users: List<Profile.FriendProfile>) {
        val userListJson = Json.encodeToString(users.toTypedArray())
        sharedPreferences.edit {
            putString(SharedPreferenceKey.USER_LIST, userListJson)
        }
    }

    fun addUser(user: Profile.FriendProfile) {
        val users = getUserList().toMutableList()
        users.add(user)
        setUserList(users)
    }

    fun removeUser(user: Profile.FriendProfile) {
        val users = getUserList().filterNot { it == user }
        setUserList(users)
    }
}
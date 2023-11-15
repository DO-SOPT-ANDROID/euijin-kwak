package org.sopt.doeuijin.data

import androidx.core.content.edit
import kotlinx.coroutines.delay
import org.sopt.common.key.SharedPreferenceKey
import org.sopt.doeuijin.container.sharedPreferences

class DefaultUserRepository {

    suspend fun setAutoLogin(isAutoLogin: Boolean) {
        delay(500)
        commitAutoLogin(isAutoLogin)
    }

    suspend fun checkAutoLogin(): Boolean {
        delay(500)
        return sharedPreferences.getBoolean(SharedPreferenceKey.AUTO_LOGIN, false)
    }

    suspend fun setUserIdentifier(id: String, pw: String, name: String) {
        sharedPreferences.edit(commit = true) {
            putString(SharedPreferenceKey.USER_ID, id)
            putString(SharedPreferenceKey.USER_PW, pw)
            putString(SharedPreferenceKey.USER_NAME, name)
        }
        delay(500)
    }

    suspend fun getUserIdentifier(): Triple<String, String, String> {
        val userId = sharedPreferences.getString(SharedPreferenceKey.USER_ID, null).orEmpty()
        val userPw = sharedPreferences.getString(SharedPreferenceKey.USER_PW, null).orEmpty()
        val userName = sharedPreferences.getString(SharedPreferenceKey.USER_NAME, null).orEmpty()
        delay(500)
        return Triple(userId, userPw, userName)
    }

    private fun commitAutoLogin(isAutoLogin: Boolean) {
        sharedPreferences.apply {
            val name = getString(SharedPreferenceKey.USER_NAME, null).orEmpty()
            edit(commit = true) {
                putBoolean(SharedPreferenceKey.AUTO_LOGIN, isAutoLogin)
            }
        }
    }
}
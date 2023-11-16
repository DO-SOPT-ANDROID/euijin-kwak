package org.sopt.doeuijin.data

import androidx.core.content.edit
import org.sopt.common.key.SharedPreferenceKey
import org.sopt.doeuijin.container.sharedPreferences

class DefaultUserRepository {

    fun setAutoLogin(
        isAutoLogin: Boolean,
    ) {
        commitAutoLogin(isAutoLogin)
    }

    fun checkAutoLogin(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferenceKey.AUTO_LOGIN, false)
    }

    fun setUserIdentifier(id: String, pw: String, name: String) {
        sharedPreferences.edit(commit = true) {
            putString(SharedPreferenceKey.USER_ID, id)
            putString(SharedPreferenceKey.USER_PW, pw)
            putString(SharedPreferenceKey.USER_NAME, name)
        }
    }

    suspend fun getUserIdentifier(): Triple<String, String, String> {
        val userId = sharedPreferences.getString(SharedPreferenceKey.USER_ID, null).orEmpty()
        val userPw = sharedPreferences.getString(SharedPreferenceKey.USER_PW, null).orEmpty()
        val userName = sharedPreferences.getString(SharedPreferenceKey.USER_NAME, null).orEmpty()
        return Triple(userId, userPw, userName)
    }

    private fun commitAutoLogin(isAutoLogin: Boolean) {
        sharedPreferences.apply {
            edit(commit = true) {
                putBoolean(SharedPreferenceKey.AUTO_LOGIN, isAutoLogin)
            }
        }
    }
}
package org.sopt.doeuijin.data

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import org.sopt.common.key.SharedPreferenceKey
import org.sopt.doeuijin.container.sharedPreferences

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultUserRepository {

    suspend fun saveAutoLogin(id: String, pw: String): Boolean =
        suspendCancellableCoroutine { continuation ->
            lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

            listener = setSharedPreferencesChageListener(continuation, listener)
            commitUserIdentifier(id, pw, listener)
            continuation.invokeOnCancellation {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(
                    listener,
                )
            }
        }

    suspend fun checkAutoLogin(): Boolean {
        delay(500)
        return suspendCancellableCoroutine { continuation ->
            val userId = sharedPreferences.getString(SharedPreferenceKey.USER_ID, null)
            val userPw = sharedPreferences.getString(SharedPreferenceKey.USER_PW, null)
            val userName = sharedPreferences.getString(SharedPreferenceKey.USER_NAME, null)
            continuation.resume(userId != null && userPw != null && userName != null, null)
        }
    }

    suspend fun getUserIdentifier(): Triple<String, String, String> {
        delay(500)
        return suspendCancellableCoroutine { continuation ->
            val userId = sharedPreferences.getString(SharedPreferenceKey.USER_ID, null).orEmpty()
            val userPw = sharedPreferences.getString(SharedPreferenceKey.USER_PW, null).orEmpty()
            val userName =
                sharedPreferences.getString(SharedPreferenceKey.USER_NAME, null).orEmpty()
            continuation.resume(Triple(userId, userPw, userName), null)
        }
    }

    private fun setSharedPreferencesChageListener(
        continuation: CancellableContinuation<Boolean>,
        listener: SharedPreferences.OnSharedPreferenceChangeListener,
    ) = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (isSaveTokenChanged(key)) {
            continuation.resume(true, null)
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(
                listener,
            )
        }
    }

    private fun commitUserIdentifier(
        id: String,
        pw: String,
        listener: SharedPreferences.OnSharedPreferenceChangeListener,
    ) {
        sharedPreferences.apply {
            val name = getString(SharedPreferenceKey.USER_NAME, null).orEmpty()
            edit(commit = true) {
                putString(SharedPreferenceKey.USER_ID, id)
                putString(SharedPreferenceKey.USER_PW, pw)
                putString(SharedPreferenceKey.USER_NAME, name)
            }
            registerOnSharedPreferenceChangeListener(listener)
        }
    }

    private fun isSaveTokenChanged(key: String?) =
        key == SharedPreferenceKey.USER_ID || key == SharedPreferenceKey.USER_PW || key == SharedPreferenceKey.USER_NAME
}
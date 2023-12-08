package org.sopt.doeuijin.data.auth.repository

import kotlinx.coroutines.suspendCancellableCoroutine
import org.sopt.common.extension.await
import org.sopt.doeuijin.data.auth.api.AuthServicePool
import org.sopt.doeuijin.data.auth.model.CheckNicknameResponse
import org.sopt.doeuijin.data.auth.model.LoginRequest
import org.sopt.doeuijin.data.auth.model.LoginResponse
import org.sopt.doeuijin.data.auth.model.MemberInfoResponse
import org.sopt.doeuijin.data.auth.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DefaultAuthRepository {
    suspend fun register(registerRequest: RegisterRequest) {
        return suspendCancellableCoroutine { continuation ->
            continuation.invokeOnCancellation {
                continuation.resumeWithException(Exception(it))
            }
            AuthServicePool.authService.register(registerRequest).enqueue(object : Callback<Unit?> {
                override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!)
                    } else {
                        continuation.resumeWithException(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<Unit?>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return AuthServicePool.authService.login(loginRequest).await()
    }

    suspend fun getMemberInfo(memberId: Int): MemberInfoResponse {
        return AuthServicePool.authService.getMemberInfo(memberId).await()
    }

    suspend fun checkNickname(nickname: String): CheckNicknameResponse {
        return AuthServicePool.authService.checkNickname(nickname).await()
    }
}
package org.sopt.doeuijin.data.auth.api.service.auth

import org.sopt.doeuijin.data.auth.model.CheckNicknameResponse
import org.sopt.doeuijin.data.auth.model.LoginRequest
import org.sopt.doeuijin.data.auth.model.LoginResponse
import org.sopt.doeuijin.data.auth.model.MemberInfoResponse
import org.sopt.doeuijin.data.auth.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {

    @POST("/api/v1/members")
    fun register(
        @Body registerRequest: RegisterRequest,
    ): Call<Unit>

    @POST("/api/v1/members/sign-in")
    fun login(
        @Body loginRequest: LoginRequest,
    ): Call<LoginResponse>

    @GET("/api/v1/members/{memberId}")
    fun getMemberInfo(
        @Path("memberId") memberId: Int,
    ): Call<MemberInfoResponse>

    @GET("/api/v1/members/check")
    fun checkNickname(
        @Query("username") id: String,
    ): Call<CheckNicknameResponse>
}
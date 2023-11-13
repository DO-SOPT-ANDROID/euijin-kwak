package org.sopt.doeuijin.data.api.service.auth

import org.sopt.doeuijin.data.model.LoginRequest
import org.sopt.doeuijin.data.model.LoginResponse
import org.sopt.doeuijin.data.model.MemberInfoResponse
import org.sopt.doeuijin.data.model.RegisterRequest
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {
    fun logout()

    @POST("/api/v1/members")
    fun register(registerRequest: RegisterRequest)

    @POST("/api/v1/members/sign-in")
    fun login(loginRequest: LoginRequest): LoginResponse

    @GET("/api/v1/members/{memberId}")
    fun getMemberInfo(
        @Path("memberId") memberId: Int,
    ): MemberInfoResponse

    @GET("/api/v1/members/check")
    fun checkNickname(
        @Query("username") id: String,
    ): Boolean
}
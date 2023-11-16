package org.sopt.doeuijin.data.user.api.service

import org.sopt.doeuijin.data.user.model.ResponseUserListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {

    @GET("/api/users")
    fun getUsers(
        @Query("page") page: Int = 2,
    ): Call<ResponseUserListDto>
}

package org.sopt.doeuijin.data.auth.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.sopt.doeuijin.data.auth.api.service.auth.AuthService
import retrofit2.Retrofit

object ApiFactory {

    lateinit var retrofit: Retrofit

    fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}

object ServicePool {
    val authService = ApiFactory.create<AuthService>()
}

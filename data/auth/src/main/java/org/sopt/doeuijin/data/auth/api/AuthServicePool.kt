package org.sopt.doeuijin.data.auth.api

import org.sopt.common.api.ApiFactory
import org.sopt.doeuijin.data.auth.api.service.auth.AuthService

object AuthServicePool {
    val authService = ApiFactory.create<AuthService>()
}
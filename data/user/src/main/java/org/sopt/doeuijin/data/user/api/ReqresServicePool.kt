package org.sopt.doeuijin.data.user.api

import org.sopt.common.api.ApiFactory
import org.sopt.doeuijin.data.user.api.service.ReqresService

object ReqresServicePool {
    val authService = ApiFactory.createReqres<ReqresService>()
}
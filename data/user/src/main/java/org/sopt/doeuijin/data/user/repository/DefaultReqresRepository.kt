package org.sopt.doeuijin.data.user.repository

import org.sopt.common.extension.await
import org.sopt.doeuijin.data.user.api.ReqresServicePool
import org.sopt.doeuijin.data.user.model.ResponseUserListDto

class DefaultReqresRepository() {

    suspend fun getUsers(page: Int): ResponseUserListDto {
        return ReqresServicePool.authService.getUsers(page).await()
    }
}

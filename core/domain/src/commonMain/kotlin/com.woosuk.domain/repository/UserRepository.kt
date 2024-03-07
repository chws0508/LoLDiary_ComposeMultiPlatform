package com.woosuk.domain.repository

import com.woosuk.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserAccount(
        gameName: String,
        tagLine: String,
        onError: suspend (code: Int?, message: String?) -> Unit,
    ): Flow<UserAccount>
}

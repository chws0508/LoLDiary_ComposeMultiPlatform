package com.woosuk.domain.repository

import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.UserAccount
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserAccount(
        gameName: String,
        tagLine: String,
        onError: suspend (ErrorState) -> Unit,
    ): Flow<UserAccount>

    suspend fun getCurrentUser(): UserAccount?

    suspend fun saveUser(userAccount: UserAccount)

    fun getAllUser(): Flow<List<UserAccount>>
}

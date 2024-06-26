package com.woosuk.domain.repository

import com.woosuk.domain.model.Account
import com.woosuk.domain.model.ErrorState
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccount(
        nickName: String,
        tag: String,
        onError: suspend (ErrorState) -> Unit,
    ): Flow<Account>

    suspend fun getCurrentAccount(): Account?

    suspend fun saveAccount(account: Account)

    fun initCurrentAccount(): Flow<Boolean>

    fun getAllAccounts(): Flow<List<Account>>
}

package com.woosuk.database

import kotlinx.coroutines.flow.Flow

interface AccountDatabaseDataSource {
    fun getAllAccounts(): Flow<List<AccountEntity>>

    suspend fun getCurrentAccount(): AccountEntity?

    suspend fun insertAccount(AccountEntity: AccountEntity)
}

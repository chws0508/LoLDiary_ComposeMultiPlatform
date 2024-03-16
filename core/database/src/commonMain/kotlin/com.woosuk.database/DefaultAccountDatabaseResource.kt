package com.woosuk.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DefaultAccountDatabaseResource(
    private val accountQueries: AccountQueries,
) : AccountDatabaseDataSource {
    override fun getAllAccounts(): Flow<List<AccountEntity>> =
        accountQueries.selectAllAccounts()
            .asFlow()
            .flowOn(Dispatchers.IO)
            .mapToList(Dispatchers.IO)

    override suspend fun getCurrentAccount(): AccountEntity? =
        accountQueries.selectCurrentAccount(1).executeAsOneOrNull()

    override suspend fun insertAccount(AccountEntity: AccountEntity) {
        accountQueries.insertFullAccount(AccountEntity)
    }
}

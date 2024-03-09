package com.woosuk.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DefaultUserDatabaseResource(
    private val userQueries: UserQueries,
) : UserDatabaseDataSource {
    override fun getAllUsers(): Flow<List<UserAccountEntity>> =
        userQueries.selectAllUsers()
            .asFlow()
            .flowOn(Dispatchers.IO)
            .mapToList(Dispatchers.IO)

    override suspend fun getCurrentUser(): UserAccountEntity? =
        userQueries.selectCurrentUser(1).executeAsOneOrNull()

    override suspend fun insertUser(userEntity: UserAccountEntity) {
        userQueries.insertFullUser(userEntity)
    }
}

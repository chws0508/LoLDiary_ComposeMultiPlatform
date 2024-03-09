package com.woosuk.database

import kotlinx.coroutines.flow.Flow

interface UserDatabaseDataSource {
    fun getAllUsers(): Flow<List<UserAccountEntity>>

    suspend fun getCurrentUser(): UserAccountEntity?

    suspend fun insertUser(userEntity: UserAccountEntity)
}

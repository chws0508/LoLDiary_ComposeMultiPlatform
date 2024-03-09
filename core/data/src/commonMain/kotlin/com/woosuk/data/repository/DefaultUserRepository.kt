package com.woosuk.data.repository

import com.skydoves.sandwich.ktor.statusCode
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.woosuk.data.mapper.toDomain
import com.woosuk.data.mapper.toEntity
import com.woosuk.database.UserDatabaseDataSource
import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.UserAccount
import com.woosuk.domain.repository.UserRepository
import com.woosuk.network.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DefaultUserRepository(
    private val userService: UserService,
    private val userDatabaseDataSource: UserDatabaseDataSource
) : UserRepository {

    override fun getUserAccount(
        gameName: String,
        tagLine: String,
        onError: suspend (ErrorState) -> Unit,
    ): Flow<UserAccount> = flow {
        userService.getUserAccount(gameName = gameName, tagLine = tagLine)
            .suspendMapSuccess {
                emit(this.toDomain())
            }.suspendOnError {
                onError(ErrorState(statusCode.code, messageOrNull))
            }.suspendOnException {
                onError(ErrorState(null, messageOrNull))
            }
    }.flowOn(Dispatchers.IO)

    override fun getAllUser(): Flow<List<UserAccount>> =
        userDatabaseDataSource.getAllUsers()
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun getCurrentUser() = userDatabaseDataSource.getCurrentUser()?.toDomain()

    override suspend fun saveUser(userAccount: UserAccount) {
        userDatabaseDataSource.insertUser(userAccount.toEntity())
    }
}

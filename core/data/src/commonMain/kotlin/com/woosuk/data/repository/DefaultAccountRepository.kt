package com.woosuk.data.repository

import com.skydoves.sandwich.ktor.statusCode
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.woosuk.data.mapper.toDomain
import com.woosuk.data.mapper.toEntity
import com.woosuk.database.AccountDatabaseDataSource
import com.woosuk.domain.model.Account
import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.repository.AccountRepository
import com.woosuk.network.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DefaultAccountRepository(
    private val userService: UserService,
    private val userDatabaseDataSource: AccountDatabaseDataSource
) : AccountRepository {

    override fun getAccount(
        nickName: String,
        tag: String,
        onError: suspend (ErrorState) -> Unit
    ): Flow<Account> = flow {
        getPuuid(nickName, tag, onError).collect { puuid ->
            userService.getSummoner(puuid).suspendMapSuccess {
                emit(
                    Account(
                        puuid = puuid,
                        summonerId = id,
                        nickName = nickName,
                        tag = tag,
                        isCurrentUser = false
                    )
                )
            }.suspendOnError {
                onError(ErrorState(statusCode.code, messageOrNull))
            }.suspendOnException {
                onError(ErrorState(ErrorState.ExceptionCode, messageOrNull))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllAccounts(): Flow<List<Account>> =
        userDatabaseDataSource.getAllAccounts()
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun getCurrentAccount(): Account? =
        userDatabaseDataSource.getCurrentAccount()?.toDomain()

    override suspend fun saveAccount(account: Account) {
        userDatabaseDataSource.insertAccount(account.toEntity(true))
    }

    private fun getPuuid(
        nickName: String,
        tag: String,
        onError: suspend (ErrorState) -> Unit
    ) = flow {
        userService.getAccount(nickName, tag).suspendMapSuccess {
            emit(puuid)
        }.suspendOnError {
            onError(ErrorState(statusCode.code, messageOrNull))
        }.suspendOnException {
            onError(ErrorState(-1, messageOrNull))
        }
    }
}

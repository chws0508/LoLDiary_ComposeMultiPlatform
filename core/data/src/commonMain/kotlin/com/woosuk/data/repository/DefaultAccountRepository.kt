package com.woosuk.data.repository

import com.skydoves.sandwich.suspendMapSuccess
import com.woosuk.data.handleError
import com.woosuk.data.mapper.toAccount
import com.woosuk.data.mapper.toAccountEntity
import com.woosuk.database.AccountDatabaseDataSource
import com.woosuk.domain.model.Account
import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.repository.AccountRepository
import com.woosuk.network.service.AccountService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DefaultAccountRepository(
    private val accountService: AccountService,
    private val userDatabaseDataSource: AccountDatabaseDataSource,
) : AccountRepository {
    override fun getAccount(
        nickName: String,
        tag: String,
        onError: suspend (ErrorState) -> Unit,
    ): Flow<Account> =
        flow {
            getPuuid(nickName, tag, onError).collect { puuid ->
                accountService.getSummoner(puuid).suspendMapSuccess {
                    emit(
                        Account(
                            puuid = puuid,
                            summonerId = id,
                            nickName = nickName,
                            tag = tag,
                            isCurrentUser = false,
                        ),
                    )
                }.handleError(onError)
            }
        }.flowOn(Dispatchers.IO)

    override fun getAllAccounts(): Flow<List<Account>> =
        userDatabaseDataSource.getAllAccounts()
            .map { entities -> entities.map { it.toAccount() } }

    override suspend fun getCurrentAccount(): Account? = userDatabaseDataSource.getCurrentAccount()?.toAccount()

    override suspend fun saveAccount(account: Account) {
        userDatabaseDataSource.insertAccount(account.toAccountEntity(true))
    }

    private fun getPuuid(
        nickName: String,
        tag: String,
        onError: suspend (ErrorState) -> Unit,
    ) = flow {
        accountService.getPuuid(nickName, tag).suspendMapSuccess {
            emit(puuid)
        }.handleError(onError)
    }
}

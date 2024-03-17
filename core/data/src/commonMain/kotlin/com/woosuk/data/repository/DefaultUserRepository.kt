package com.woosuk.data.repository

import com.skydoves.sandwich.getOrThrow
import com.woosuk.data.mapper.toRankInfo
import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.User
import com.woosuk.domain.repository.AccountRepository
import com.woosuk.domain.repository.UserRepository
import com.woosuk.network.service.AccountService
import com.woosuk.network.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultUserRepository(
    private val userService: UserService,
    private val accountService: AccountService,
    private val accountRepository: AccountRepository,
) : UserRepository {
    override fun getCurrentUser(onError: (ErrorState) -> Unit): Flow<User> =
        flow {
            val currentAccount =
                accountRepository.getCurrentAccount() ?: throw IllegalStateException("계정 정보 불러오기 실패")
            val summonerDto = accountService.getSummoner(currentAccount.puuid).getOrThrow()
            val rankInfo =
                userService.gerRankInfo(currentAccount.summonerId).getOrThrow().first().toRankInfo()
            emit(
                User(
                    account = currentAccount,
                    rankInfo = rankInfo,
                    profileIconId = summonerDto.profileIconId,
                    level = summonerDto.summonerLevel,
                ),
            )
        }.catch { onError(ErrorState(500, it.message)) }
            .flowOn(Dispatchers.IO)
}

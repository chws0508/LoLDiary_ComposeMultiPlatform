package com.woosuk.data.repository

import com.skydoves.sandwich.ktor.statusCode
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.woosuk.data.mapper.toDomain
import com.woosuk.domain.model.UserAccount
import com.woosuk.domain.repository.UserRepository
import com.woosuk.network.service.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserRepository(
    private val userService: UserService,
) : UserRepository {
    override fun getUserAccount(
        gameName: String,
        tagLine: String,
        onError: suspend (errorCode: Int?, message: String?) -> Unit,
    ): Flow<UserAccount> = flow {
        userService.getUserAccount(gameName = gameName, tagLine = tagLine)
            .suspendMapSuccess {
                emit(this.toDomain())
            }.suspendOnError {
                onError(statusCode.code, messageOrNull)
            }.suspendOnException {
                onError(null, messageOrNull)
            }
    }
}

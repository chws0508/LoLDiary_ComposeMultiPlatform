package com.woosuk.domain.usecase

import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.repository.AccountRepository
import kotlinx.coroutines.flow.onEach

class LoginUseCase(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(
        nickName: String,
        tag: String,
        onError: suspend (ErrorState) -> Unit,
    ) = accountRepository.getAccount(
        nickName = nickName,
        tag = tag,
        onError = onError
    ).onEach { accountRepository.saveAccount(it.copy(isCurrentUser = true)) }
}

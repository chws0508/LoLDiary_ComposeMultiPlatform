package com.woosuk.domain.usecase

import com.woosuk.domain.repository.AccountRepository

class LogOutUseCase(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke() = accountRepository.initCurrentAccount()
}

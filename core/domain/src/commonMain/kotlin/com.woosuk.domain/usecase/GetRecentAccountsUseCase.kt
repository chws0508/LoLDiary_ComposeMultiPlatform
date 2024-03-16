package com.woosuk.domain.usecase

import com.woosuk.domain.repository.AccountRepository

class GetRecentAccountsUseCase(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke() = accountRepository.getAllAccounts()
}

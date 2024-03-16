package com.woosuk.domain.usecase

import com.woosuk.domain.repository.AccountRepository

class GetRecentUsersUseCase(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke() = accountRepository.getAllAccounts()
}

package com.woosuk.domain.usecase

import com.woosuk.domain.repository.AccountRepository

class GetCurrentUserUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = accountRepository.getCurrentAccount()
}

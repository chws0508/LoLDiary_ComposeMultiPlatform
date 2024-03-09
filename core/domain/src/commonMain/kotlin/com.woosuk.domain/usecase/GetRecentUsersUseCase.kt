package com.woosuk.domain.usecase

import com.woosuk.domain.repository.UserRepository

class GetRecentUsersUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke() = userRepository.getAllUser()
}

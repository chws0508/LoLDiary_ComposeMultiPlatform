package com.woosuk.domain.usecase

import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.model.User
import com.woosuk.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(onError: (ErrorState) -> Unit): Flow<User> {
        return userRepository.getCurrentUser(onError = onError)
    }
}

package com.woosuk.domain.usecase

import com.woosuk.domain.model.ErrorState
import com.woosuk.domain.repository.UserRepository
import kotlinx.coroutines.flow.onEach

class LoginUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        gameName: String,
        tagLine: String,
        onError: suspend (ErrorState) -> Unit,
    ) = userRepository.getUserAccount(
        gameName = gameName,
        tagLine = tagLine,
        onError = onError
    ).onEach { userRepository.saveUser(it.copy(isCurrentUser = true)) }
}

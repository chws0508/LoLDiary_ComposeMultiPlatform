package com.woosuk.domain.usecase

import com.woosuk.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        gameName: String,
        tagLine: String,
        onError: suspend (errorCode: Int?, message: String?) -> Unit,
    ) = userRepository.getUserAccount(
        gameName = gameName,
        tagLine = tagLine,
        onError = onError
    )
}

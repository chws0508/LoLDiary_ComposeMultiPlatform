package com.woosuk.onboarding

sealed interface OnBoardingSideEffect {
    data object LoginSuccess : OnBoardingSideEffect
    data class LoginFail(val message: String) : OnBoardingSideEffect
}

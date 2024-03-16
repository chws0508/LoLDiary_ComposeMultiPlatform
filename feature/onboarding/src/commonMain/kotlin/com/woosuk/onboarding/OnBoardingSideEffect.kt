package com.woosuk.onboarding

import com.woosuk.domain.model.ErrorState

sealed interface OnBoardingSideEffect {
    data object LoginSuccess : OnBoardingSideEffect
    data class LoginFail(val errorState: ErrorState) : OnBoardingSideEffect
}

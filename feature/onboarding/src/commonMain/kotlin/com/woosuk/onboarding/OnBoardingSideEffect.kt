package com.woosuk.onboarding

import com.woosuk.domain.model.ErrorState

sealed interface OnBoardingSideEffect {
    data class LoginSuccess(val puuid: String) : OnBoardingSideEffect

    data class LoginFail(val errorState: ErrorState) : OnBoardingSideEffect
}

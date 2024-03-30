package com.woosuk.home

sealed interface HomeSideEffect {
    data object UserInfoLoadFail : HomeSideEffect

    data object MatchInfoLoadFail : HomeSideEffect
}

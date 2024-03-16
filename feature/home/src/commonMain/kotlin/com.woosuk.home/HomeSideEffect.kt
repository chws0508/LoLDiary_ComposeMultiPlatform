package com.woosuk.home

sealed interface HomeSideEffect {
    data object LoadFail : HomeSideEffect
}

package com.woosuk.matchdetails

sealed interface MatchDetailsUiEvent {
    data object NetworkError : MatchDetailsUiEvent
}

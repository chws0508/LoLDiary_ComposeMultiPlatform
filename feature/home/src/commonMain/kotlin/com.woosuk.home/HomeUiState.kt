package com.woosuk.home

import com.woosuk.domain.model.User

sealed interface UserUiState {
    data object Loading : UserUiState

    data class Success(val user: User) : UserUiState

    data object Fail : UserUiState
}

sealed interface MatchUiState

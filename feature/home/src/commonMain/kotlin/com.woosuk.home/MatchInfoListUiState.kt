package com.woosuk.home

import com.woosuk.domain.model.match.UserMatchInfo

sealed interface MatchInfoListUiState {
    data object Loading : MatchInfoListUiState

    data class Success(val data: List<UserMatchInfo>)

    data object Fail : MatchInfoListUiState
}

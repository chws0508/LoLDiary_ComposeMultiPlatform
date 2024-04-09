package com.woosuk.matchdetails

import com.woosuk.domain.model.match.MatchDetails

sealed interface MatchDetailsUiState {
    data object FailByNetwork : MatchDetailsUiState

    data object Loading : MatchDetailsUiState

    data class Success(
        val data: MatchDetails,
    ) : MatchDetailsUiState
}

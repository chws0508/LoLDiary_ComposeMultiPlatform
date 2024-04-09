package com.woosuk.matchdetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.domain.usecase.GetMatchDetailsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchDetailsScreenModel(
    private val getMatchDetailsUseCase: GetMatchDetailsUseCase,
) : ScreenModel {
    private val _uiEvent = Channel<MatchDetailsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _matchId = MutableStateFlow<String?>(null)
    val matchId = _matchId.asStateFlow()

    private val _matchDetailsUiState =
        MutableStateFlow<MatchDetailsUiState>(MatchDetailsUiState.Loading)
    val matchDetailsUiState = _matchDetailsUiState.asStateFlow()

    init {
        screenModelScope.launch {
            matchId.collectLatest { matchId ->
                if (matchId != null) {
                    getMatchDetailsUseCase(matchId) {
                        _uiEvent.send(MatchDetailsUiEvent.NetworkError)
                    }.collectLatest { matchDetails ->
                        _matchDetailsUiState.update { MatchDetailsUiState.Success(matchDetails) }
                    }
                }
            }
        }
    }

    fun initMatchId(matchId: String) {
        _matchId.update { matchId }
    }
}

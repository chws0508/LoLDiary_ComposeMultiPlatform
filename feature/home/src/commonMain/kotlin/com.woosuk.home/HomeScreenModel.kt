package com.woosuk.home

import androidx.paging.PagingData
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.domain.model.match.UserMatchInfo
import com.woosuk.domain.usecase.GetCurrentUserUseCase
import com.woosuk.domain.usecase.GetUserMatchInfoListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserMatchInfoListUseCase: GetUserMatchInfoListUseCase,
) : ScreenModel {
    private val _userUiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userUiState = _userUiState.asStateFlow()

    private val _matchInfoList = MutableStateFlow<PagingData<UserMatchInfo>>(PagingData.empty())
    val matchInfoList = _matchInfoList.asStateFlow()

    private val _sideEffect = Channel<HomeSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        getCurrentUser()
        fetchMatchInfoList()
    }

    private fun getCurrentUser() {
        getCurrentUserUseCase(onError = { _userUiState.update { UserUiState.Fail } })
            .onStart { _userUiState.update { UserUiState.Loading } }
            .onEach { user ->
                _userUiState.update { UserUiState.Success(user) }
            }.launchIn(screenModelScope)
    }

    private fun fetchMatchInfoList() {
        screenModelScope.launch {
            userUiState.collectLatest {
                when (it) {
                    UserUiState.Fail -> _matchInfoList.update { PagingData.empty() }
                    UserUiState.Loading -> _matchInfoList.update { PagingData.empty() }
                    is UserUiState.Success -> {
                        val pagingData =
                            getUserMatchInfoListUseCase(
                                loadSize = LOAD_SIZE,
                                puuid = it.user.account.puuid,
                                onError = {
                                    screenModelScope.launch {
                                        _sideEffect.send(HomeSideEffect.MatchInfoLoadFail)
                                    }
                                },
                            ).cachedIn(screenModelScope).firstOrNull() ?: PagingData.empty()
                        _matchInfoList.update { pagingData }
                    }
                }
            }
        }
    }

    companion object {
        private const val LOAD_SIZE = 20
    }
}

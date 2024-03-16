package com.woosuk.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.domain.usecase.GetCurrentUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class HomeScreenModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : ScreenModel {
    private val _userUiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userUiState = _userUiState.asStateFlow()

    private val _sideEffect = Channel<HomeSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        getCurrentUser()
    }

    fun getCurrentUser() {
        getCurrentUserUseCase {
            _userUiState.update { UserUiState.Fail }
            println(it.errorMessage)
        }
            .onStart { _userUiState.update { UserUiState.Loading } }
            .onEach { user ->
                _userUiState.update { UserUiState.Success(user) }
            }.launchIn(screenModelScope)
    }
}

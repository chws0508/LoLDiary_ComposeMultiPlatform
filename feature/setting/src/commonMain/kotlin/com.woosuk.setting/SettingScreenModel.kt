package com.woosuk.setting

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.domain.usecase.GetCurrentUserUseCase
import com.woosuk.domain.usecase.LogOutUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingScreenModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logOutUseCase: LogOutUseCase,
) : ScreenModel {
    private val _user = MutableStateFlow<SettingUiState>(SettingUiState.Loading)
    val user = _user.asStateFlow()

    private val _logoutSuccess = Channel<Boolean>()
    val logoutSuccess = _logoutSuccess.receiveAsFlow()

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        screenModelScope.launch {
            getCurrentUserUseCase(
                onError = { _user.update { SettingUiState.Fail } },
            ).onStart {
                _user.update { SettingUiState.Loading }
            }.collect { user ->
                _user.update { SettingUiState.Success(user) }
            }
        }
    }

    fun logOut() {
        logOutUseCase().onEach { success ->
            if (success) {
                _logoutSuccess.send(true)
            } else {
                _logoutSuccess.send(false)
            }
        }.launchIn(screenModelScope)
    }
}

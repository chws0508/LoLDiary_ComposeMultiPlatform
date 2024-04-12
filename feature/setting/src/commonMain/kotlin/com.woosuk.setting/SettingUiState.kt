package com.woosuk.setting

import com.woosuk.domain.model.User

sealed interface SettingUiState {
    data object Fail : SettingUiState

    data object Loading : SettingUiState

    data class Success(val user: User) : SettingUiState
}

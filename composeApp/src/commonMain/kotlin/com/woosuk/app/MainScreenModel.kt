package com.woosuk.app

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.domain.usecase.GetCurrentUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainScreenModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ScreenModel {

    private val _isLogin = Channel<Boolean>()
    val isLogin = _isLogin.receiveAsFlow()

    init {
        screenModelScope.launch {
            if (getCurrentUserUseCase() == null) _isLogin.send(false)
            else _isLogin.send(true)
        }
    }
}

package com.woosuk.onboarding

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.domain.model.LoginInfo
import com.woosuk.domain.usecase.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class OnboardingScreenModel(
    private val loginUseCase: LoginUseCase
) : ScreenModel {

    private val _sideEffect: Channel<OnBoardingSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _loginInfo = MutableStateFlow(LoginInfo("", ""))
    val loginInfo = _loginInfo.asStateFlow()

    fun login(gameName: String, tagLine: String) {
        loginUseCase(
            gameName = gameName,
            tagLine = tagLine,
        ) { erroCode, message ->
            _sideEffect.send(OnBoardingSideEffect.LoginFail(message.toString()))
        }.onEach {
            _sideEffect.send(OnBoardingSideEffect.LoginSuccess)
        }.launchIn(screenModelScope)
    }

    fun onNicknameChanged(inputNickName: String) {
        _loginInfo.update { it.copy(nickName = inputNickName) }
    }

    fun onTagChanged(inputTag: String) {
        _loginInfo.update { it.copy(tag = inputTag) }
    }
}

package com.woosuk.onboarding

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.domain.model.LoginInfo
import com.woosuk.domain.usecase.GetRecentAccountsUseCase
import com.woosuk.domain.usecase.LoginUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingScreenModel(
    private val loginUseCase: LoginUseCase,
    getRecentUsersUseCase: GetRecentAccountsUseCase,
) : ScreenModel {

    private val _sideEffect: Channel<OnBoardingSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _loginInfo = MutableStateFlow(LoginInfo("", ""))
    val loginInfo = _loginInfo.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val recentLoginUsers = getRecentUsersUseCase().stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList(),
    )

    private lateinit var loadingJob: Job

    fun login(gameName: String, tagLine: String) {
        loginUseCase(
            nickName = gameName,
            tag = tagLine,
            onError = { errorState -> _sideEffect.send(OnBoardingSideEffect.LoginFail(errorState)) }
        ).onStart {
            loadingJob =screenModelScope.launch {
                delay(500L)
                _isLoading.update { true }
            }
        }.onEach {
            _sideEffect.send(OnBoardingSideEffect.LoginSuccess)
        }.onCompletion {
            loadingJob.cancel()
            _isLoading.update { false }
        }.launchIn(screenModelScope)
    }

    fun onNicknameChanged(inputNickName: String) {
        _loginInfo.update { it.copy(nickName = inputNickName) }
    }

    fun onTagChanged(inputTag: String) {
        _loginInfo.update { it.copy(tag = inputTag) }
    }
}

package com.woosuk.app

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.woosuk.data.repository.RuneRepository
import com.woosuk.domain.repository.UrlRepository
import com.woosuk.domain.usecase.GetCurrentAccountUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainScreenModel(
    private val getCurrentAccountUseCase: GetCurrentAccountUseCase,
    private val urlRepository: UrlRepository,
) : ScreenModel {
    private val _isLogin = Channel<Boolean>()
    val isLogin = _isLogin.receiveAsFlow()

    init {
        screenModelScope.launch {
            // 초기에 사진 URL 과 로그인 정보를 파악한다
            // CdnUrlPrefix와 룬정보를 모두 업데이트 한 후에, 다음 화면으로 넘어간다.
            urlRepository.updateCdnUrlPrefix().collectLatest {
                RuneRepository.updateRunes(it).collect {
                    if (getCurrentAccountUseCase() == null) {
                        _isLogin.send(false)
                    } else {
                        _isLogin.send(true)
                    }
                }
            }
        }
    }
}

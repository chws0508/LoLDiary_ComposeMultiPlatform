package com.woosuk.navigation

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedScreenModel : ScreenModel {
    private val _showBottomBar = MutableStateFlow(false)
    val showBottomBar = _showBottomBar.asStateFlow()

    fun setBottomBarVisibility(value: Boolean) {
        _showBottomBar.update { value }
    }
}
